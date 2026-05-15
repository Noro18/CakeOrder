package com.example.ordermanagementcake.ui.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ordermanagementcake.R
import com.example.ordermanagementcake.data.local.entities.OrderStatus
import com.example.ordermanagementcake.data.local.relations.OrderWithCakes

// ── helpers ──────────────────────────────────────────────────────────────────

/** Returns the next logical status, or null if the order is already terminal. */
fun nextStatus(current: OrderStatus): OrderStatus? = when (current) {
    OrderStatus.PENDING     -> OrderStatus.IN_PROGRESS
    OrderStatus.IN_PROGRESS -> OrderStatus.READY
    OrderStatus.READY       -> OrderStatus.COMPLETED
    else                    -> null   // COMPLETED / CANCELLED — nothing to do
}

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

// ── screen ───────────────────────────────────────────────────────────────────

@Composable
fun OrderListScreen(viewModel: OrderViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val statusFilters = listOf(
        OrderStatus.PENDING,
        OrderStatus.IN_PROGRESS,
        OrderStatus.READY,
        OrderStatus.COMPLETED
    )
    val statusLabels = listOf("Pendente", "Prosesu", "Prontu", "Kompleta")

    var searchText by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Text(
                    text = "Order sira iha agora",
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
                    .padding(horizontal = 16.dp),
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
            uiState.orders.isEmpty() -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "La iha order ba status ne'e.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            }
            else -> {
                items(uiState.orders, key = { it.orders.id }) { orderWithCakes ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        OrderCard(
                            orderWithCakes = orderWithCakes,
                            onConfirmStatusUpdate = { newStatus ->
                                viewModel.updateStatus(orderWithCakes.orders.id, newStatus)
                            }
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
    onConfirmStatusUpdate: (OrderStatus) -> Unit
) {
    val order     = orderWithCakes.orders
    val mainCake  = orderWithCakes.cakes.firstOrNull()
    val next      = nextStatus(order.status) // foti status tuir mai

    // Controls whether the confirmation dialog is visible
    var showDialog by remember { mutableStateOf(false) }

    // Confirmation dialog
    if (showDialog && next != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Confirm Status Update",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = confirmMessage(order.status, mainCake?.cakeTitle ?: "this order")
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
                    Text("Yes, confirm", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDialog = false },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }

    // Card UI
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Top row — cake image + info + delivery date
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.double_chodolate_fudge),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = mainCake?.cakeTitle ?: "Keku Deskonhesidu",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Order #${order.id} • ${order.orderNotes.ifBlank { "La iha nota" }}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Entrega",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = order.deliveryDate,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))

            // Bottom row — current status badge + action button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Current status badge (read-only, just shows where it is now)
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

                // Action button — only shown if there is a next status to move to
                if (next != null) {
                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier.height(34.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = statusColor(next)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = actionLabel(order.status),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    // Terminal state — no button, just a subtle label
                    Text(
                        text = if (order.status == OrderStatus.COMPLETED) "Done ✓" else "Cancelled",
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor(order.status),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}