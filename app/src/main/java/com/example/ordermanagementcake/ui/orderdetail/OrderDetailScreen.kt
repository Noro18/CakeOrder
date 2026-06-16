package com.example.ordermanagementcake.ui.orderdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.data.local.entities.*
import com.example.ordermanagementcake.data.local.relations.CakeWithTiers
import com.example.ordermanagementcake.data.local.relations.OrderFullDetail
import com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    orderDetail: OrderFullDetail?,
    onBackClick: () -> Unit = {},
    onEditOrder: (Int) -> Unit = {},
    onStatusChange: (OrderStatus) -> Unit = {}
) {
    if (orderDetail == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val order = orderDetail.order
    val client = orderDetail.client
    val cakes = orderDetail.cakes

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles Pedidu", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onEditOrder(order.id) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        },
        bottomBar = {
            OrderDetailBottomBar(totalPrice = order.totalPrice)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Status and ID Header
            HeaderSection(orderId = order.id, status = order.status)

            Spacer(modifier = Modifier.height(32.dp))

            // Client Card
            ClientCard(client = client)

            Spacer(modifier = Modifier.height(32.dp))

            // Dates Section
            DatesSection(orderDate = order.orderDate, deliveryDate = order.deliveryDate)

            Spacer(modifier = Modifier.height(32.dp))

            // Cakes Section
            Text(
                text = "Item sira ne'ebé Pedidu",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            cakes.forEach { cakeWithTiers ->
                CakeItem(cakeWithTiers = cakeWithTiers)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notes Section
            if (order.orderNotes.isNotEmpty()) {
                Text(
                    text = "Nota Enkomenda",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = order.orderNotes,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun HeaderSection(orderId: Int, status: OrderStatus) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "PEDIDU ID",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "#BK-${String.format("%04d", orderId)}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }
        
        val statusColor = when (status) {
            OrderStatus.PENDING -> Color(0xFFEE8111)
            OrderStatus.IN_PROGRESS -> Color(0xFFF87146)
            OrderStatus.READY -> Color(0xFF31912E)
            OrderStatus.COMPLETED -> Color(0xFF3B82F6)
            OrderStatus.CANCELLED -> Color(0xFF9E9E9E)
        }

        Surface(
            color = statusColor.copy(alpha = 0.1f),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, statusColor.copy(alpha = 0.2f))
        ) {
            Text(
                text = status.name,
                color = statusColor,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ClientCard(client: ClientEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = client.name.take(1).uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = client.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = client.phone,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun DatesSection(orderDate: String, deliveryDate: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        DateItem(
            label = "DATA ORDENADU",
            date = orderDate,
            icon = Icons.Default.CalendarToday,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        DateItem(
            label = "DATA ENTREGA",
            date = deliveryDate,
            icon = Icons.Default.LocalShipping,
            modifier = Modifier.weight(1f),
            highlight = true
        )
    }
}

@Composable
fun DateItem(label: String, date: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier, highlight: Boolean = false) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = if (highlight) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface,
            border = BorderStroke(
                1.dp,
                if (highlight) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.outlineVariant
            )
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if (highlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun CakeItem(cakeWithTiers: CakeWithTiers) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Cake,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = cakeWithTiers.cake.cakeTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "${cakeWithTiers.tiers.size} " + if (cakeWithTiers.tiers.size == 1) "Tier" else "Tiers",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (cakeWithTiers.cake.cakeNotes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = cakeWithTiers.cake.cakeNotes,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(16.dp))

            // Tier Specifications
            cakeWithTiers.tiers.sortedBy { it.level }.forEach { tier ->
                TierSpecRow(tier = tier)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TierSpecRow(tier: TierEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Color Swatch
        val color = try {
            Color(android.graphics.Color.parseColor(tier.colorHex))
        } catch (e: Exception) {
            MaterialTheme.colorScheme.primaryContainer
        }
        
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
                .border(0.5.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = "Tier ${tier.level}",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(60.dp)
        )

        Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Text(
                text = "${tier.sizeId}\" · ${if (tier.shapeId == 1) "Round" else "Square"}",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Text(
            text = "$${String.format("%.2f", tier.price)}",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun OrderDetailBottomBar(totalPrice: Double) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "TOTÁL AMONTU",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = { /* Confirm or Action */ },
                modifier = Modifier
                    .height(56.dp)
                    .width(160.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Konfirma", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderDetailScreenPreview() {
    val dummyClient = ClientEntity(
        id = 1,
        name = "Elena Rodriguez",
        phone = "+670 7712 3456",
        address = "Dili, Timor-Leste",
        notes = "Preferente kór mutin"
    )

    val dummyOrder = OrderEntity(
        id = 8842,
        customerId = 1,
        orderDate = "Jun 10, 2026",
        deliveryDate = "Jun 16, 2026",
        totalPrice = 195.0,
        orderNotes = "Keke espesiál ba tinan 15. Favor foti iha dader."
    )

    val dummyCakes = listOf(
        CakeWithTiers(
            cake = CakeEntity(id = 1, orderId = 8842, cakeTitle = "Midnight Ganache", cakeNotes = "Chocolate extra"),
            tiers = listOf(
                TierEntity(id = 1, cakeId = 1, level = 1, shapeId = 1, sizeId = 3, colorHex = "#4E342E", price = 50.0),
                TierEntity(id = 2, cakeId = 1, level = 2, shapeId = 1, sizeId = 2, colorHex = "#5D4037", price = 35.0)
            )
        ),
        CakeWithTiers(
            cake = CakeEntity(id = 2, orderId = 8842, cakeTitle = "Vanilla Ruffle", cakeNotes = "Uza foz mutin"),
            tiers = listOf(
                TierEntity(id = 3, cakeId = 2, level = 1, shapeId = 2, sizeId = 4, colorHex = "#F5F5F5", price = 110.0)
            )
        )
    )

    val dummyDetail = OrderFullDetail(
        order = dummyOrder,
        client = dummyClient,
        cakes = dummyCakes
    )

    OrderManagementCakeTheme {
        OrderDetailScreen(orderDetail = dummyDetail)
    }
}
