package com.example.ordermanagementcake.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ordermanagementcake.ui.theme.extendedColors
import java.util.Locale

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onAddClient: () -> Unit,
    onAddOrder: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp, bottom = 24.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            // ── STATS GRID ───────────────────────────────
            Text(
                text = "Dadus Jerál",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                CompactStatCard(
                    label = "Loron Ohin",
                    value = state.todayOrderCount.toString(),
                    icon = Icons.Default.Today,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                CompactStatCard(
                    label = "Hein-hela",
                    value = state.pendingCount.toString(),
                    icon = Icons.Default.PendingActions,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                CompactStatCard(
                    label = "Pronto",
                    value = state.readyCount.toString(),
                    icon = Icons.Default.CheckCircle,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                CompactStatCard(
                    label = "Kliente",
                    value = state.totalClients.toString(),
                    icon = Icons.Default.Group,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── QUICK ACTIONS ────────────────────────────
            Text(
                text = "Asaun Lais",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionItem(
                    label = "Order Foun",
                    icon = Icons.Default.AddShoppingCart,
                    modifier = Modifier.weight(1f),
                    onClick = onAddOrder
                )
                QuickActionItem(
                    label = "Kliente Foun",
                    icon = Icons.Default.PersonAdd,
                    modifier = Modifier.weight(1f),
                    onClick = onAddClient
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── REVENUE SUMMARY CARD ─────────────────────
            RevenueCard(revenue = state.totalRevenue)

            Spacer(modifier = Modifier.height(32.dp))

            // ── UPCOMING DEADLINES ────────────────────────
            SectionHeader(title = "Rekolla tuir mai", actionText = "Haree hotu")
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (state.pickupItems.isEmpty()) {
                    Text(
                        text = "Liha rekolla ne'ebe mai",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    state.pickupItems.forEach { item ->
                        UpcomingPickupCard(
                            client = item.client,
                            cake = item.cake,
                            daysLeft = item.daysLeft,
                            isUrgent = item.isUrgent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── TODAY'S TIMELINE ─────────────────────────
            SectionHeader(title = "Orario Ohin", actionText = "Kalendario")
            Spacer(modifier = Modifier.height(16.dp))

            if (state.timelineItems.isEmpty()) {
                Text(
                    text = "Liha servisu ohin",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                state.timelineItems.forEachIndexed { index, item ->
                    TimelineItem(
                        time = item.time,
                        client = item.client,
                        cake = item.cake,
                        status = item.status,
                        isLast = index == state.timelineItems.lastIndex
                    )
                }
            }
        }
    }
}

@Composable
fun CompactStatCard(label: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.extendedColors.surfaceContainerLow,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RevenueCard(revenue: Double) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 1.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(100.dp)
                    .offset(x = 20.dp, y = 20.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )

            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Total Rendimentu",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$${String.format(Locale.US, "%,.2f", revenue)}",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun QuickActionItem(label: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun UpcomingPickupCard(client: String, cake: String, daysLeft: String, isUrgent: Boolean) {
    Surface(
        modifier = Modifier.width(200.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.extendedColors.surfaceContainerLow,
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                color = if (isUrgent) MaterialTheme.colorScheme.errorContainer
                        else MaterialTheme.colorScheme.secondaryContainer,
                shape = CircleShape
            ) {
                Text(
                    text = daysLeft,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isUrgent) MaterialTheme.colorScheme.onErrorContainer
                            else MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = client,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = cake,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Custom Order",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun TimelineItem(time: String, client: String, cake: String, status: String, isLast: Boolean) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(60.dp)
        ) {
            Text(
                text = time.take(10),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = status,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!isLast) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(60.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Card(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.extendedColors.surfaceContainerLow
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = client, fontWeight = FontWeight.Bold)
                    Text(
                        text = cake,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    color = if (status == "Pronto") MaterialTheme.extendedColors.successContainer
                            else MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                ) {
                    Text(
                        text = status,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, actionText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        TextButton(onClick = { /* TODO */ }) {
            Text(text = actionText, color = MaterialTheme.colorScheme.primary)
        }
    }
}
