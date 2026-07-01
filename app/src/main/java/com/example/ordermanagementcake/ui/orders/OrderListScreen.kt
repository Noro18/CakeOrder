package com.example.ordermanagementcake.ui.orders


import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ordermanagementcake.R
import com.example.ordermanagementcake.data.local.entities.OrderStatus
import com.example.ordermanagementcake.data.local.relations.OrderWithCakes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ── helpers ──────────────────────────────────────────────────────────────────

/** Human-readable action label for the button, e.g. "Start Baking" */
fun actionLabel(current: OrderStatus): String = when (current) {
    OrderStatus.PENDING     -> "Komesa Baking"
    OrderStatus.IN_PROGRESS -> "Marka Prontu"
    OrderStatus.READY       -> "Marka Kompletu"
    OrderStatus.COMPLETED   -> "Completu ✓"
    OrderStatus.CANCELLED   -> "Kansela"
}

/** Confirmation dialog message */
fun confirmMessage(current: OrderStatus, cakeTitle: String): String = when (current) {
    OrderStatus.PENDING     -> "Komesa Bake \"$cakeTitle\"? Ida ne'e sei muda statu kek ba iha Prosesa."
    OrderStatus.IN_PROGRESS -> "Marka \"$cakeTitle\" Prontu atu foti?"
    OrderStatus.READY       -> "Marka \"$cakeTitle\" Kompleta? This cannot be undone."
    else                    -> ""
}

/** Button / badge colour per status */
fun statusColor(status: OrderStatus): Color = when (status) {
    OrderStatus.PENDING     -> Color(0xFFEE8111)
    OrderStatus.IN_PROGRESS -> Color(0xFFF87146)
    OrderStatus.READY       -> Color(0xFF31912E)
    OrderStatus.COMPLETED   -> Color(0xFF3B82F6)
    OrderStatus.CANCELLED   -> Color(0xFF9E9E9E)
}

fun formatDeliveryDate(raw: String): String = try {
    val input  = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
    val output = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    output.format(input.parse(raw) ?: Date())
} catch (_: Exception) { raw }

// ── screen ───────────────────────────────────────────────────────────────────

@Composable
fun OrderListScreen(
    viewModel: OrderViewModel,
    onOrderClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val statusFilters = listOf(
        OrderStatus.PENDING,
        OrderStatus.IN_PROGRESS,
        OrderStatus.READY,
        OrderStatus.COMPLETED
    )
    val statusLabels = listOf("Pendente", "Prosesu", "Prontu", "Kompleta")

    var searchText by remember { mutableStateOf("") }
    var isSearchFocused by remember { mutableStateOf(false) }

    val filteredOrders = remember(uiState.orders, searchText) {
        if (searchText.isBlank()) uiState.orders
        else uiState.orders.filter { owc ->
            val o = owc.orders
            val cake = owc.cakes.firstOrNull()
            o.id.toString().contains(searchText, ignoreCase = true) ||
            cake?.cakeTitle?.contains(searchText, ignoreCase = true) == true ||
            o.orderNotes.contains(searchText, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        contentPadding = PaddingValues(bottom = 88.dp)
    ) {
        // Header
        if (!isSearchFocused) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = "Pedidu sira iha agora",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Jestiona ita-nia kriasaun kulinária",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buka order...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged { isSearchFocused = it.isFocused },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )
        }

        // Filter Chips
        if (!isSearchFocused) {
            item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                statusFilters.forEachIndexed { index, status ->
                    val isSelected = uiState.selectedStatus == status
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.loadOrders(status) },
                        label = { Text(statusLabels[index]) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFF87146),
                            selectedLabelColor = Color.White
                        ),
                        border = if (!isSelected) FilterChipDefaults.filterChipBorder(
                            borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            enabled = true,
                            selected = false
                        ) else null
                    )
                }
            }
        }
        }

        // Content
        when {
            uiState.isLoading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(32.dp),
                            color = Color(0xFFF87146)
                        )
                    }
                }
            }
            uiState.errorMessage != null -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Error: ${uiState.errorMessage}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
            filteredOrders.isEmpty() -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "La iha order ba estadu ne'e.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            }
            else -> {
                items(filteredOrders, key = { it.orders.id }) { orderWithCakes ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        OrderCard(
                            orderWithCakes = orderWithCakes,
                            onConfirmStatusUpdate = { newStatus ->
                                viewModel.updateStatus(orderWithCakes.orders.id, newStatus)
                            },
                            onCardClick = { onOrderClick(orderWithCakes.orders.id) }
                        )
                    }
                }
            }
        }
    }
}

// ── card ─────────────────────────────────────────────────────────────────────

@Composable
fun OrderCard(
    orderWithCakes: OrderWithCakes,
    onConfirmStatusUpdate: (OrderStatus) -> Unit,
    onCardClick: () -> Unit
) {
    val order     = orderWithCakes.orders
    val mainCake  = orderWithCakes.cakes.firstOrNull()
    val next      = order.status.nextStatus() // foti status tuir mai

    // Controls whether the confirmation dialog is visible
    var showDialog by remember { mutableStateOf(false) }

    // Confirmation dialog
    if (showDialog && next != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Konfirma mudansa estadu",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = confirmMessage(order.status, mainCake?.cakeTitle ?: "orderan ida ne'e")
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirmStatusUpdate(next)
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = statusColor(next)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Sim, Konfirma", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDialog = false },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Kansela")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    // Card UI
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header row — order info + delivery date
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Order #${order.id}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        // Status badge
                        Surface(
                            color = statusColor(order.status).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = order.status.name.replace("_", " "),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                color = statusColor(order.status),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = mainCake?.cakeTitle ?: "Keku Deskonhesidu",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = order.orderNotes.ifBlank { "La iha nota" },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Entrega",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = formatDeliveryDate(order.deliveryDate),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(12.dp))

            // Footer row — action button or terminal state
            Box(modifier = Modifier.fillMaxWidth()) {
                if (next != null) {
                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = statusColor(next)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = actionLabel(order.status),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (order.status == OrderStatus.COMPLETED) "Order hotu ✓" else "Order kanseladu",
                                style = MaterialTheme.typography.bodyMedium,
                                color = statusColor(order.status),
                                fontWeight = FontWeight.Bold
                            )
                            if (order.status == OrderStatus.COMPLETED) {
                                Text(
                                    text = "Klients reseve ona",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                        
                        Icon(
                            imageVector = if (order.status == OrderStatus.COMPLETED) 
                                Icons.Filled.CheckCircle
                            else 
                                Icons.Filled.Cancel,
                            contentDescription = null,
                            tint = statusColor(order.status),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}