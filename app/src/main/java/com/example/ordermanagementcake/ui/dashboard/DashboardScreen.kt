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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.R
import com.example.ordermanagementcake.ui.theme.extendedColors

@Composable
fun DashboardScreen() {
    val extendedColors = MaterialTheme.extendedColors
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp, bottom = 24.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            
            // ── 2. STATS GRID (COMPACT) ───────────────────────
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(modifier = Modifier.fillMaxWidth()) {
                CompactStatCard(
                    label = "Loron Ohin",
                    value = "12",
                    icon = Icons.Default.Today,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                CompactStatCard(
                    label = "Pending",
                    value = "4",
                    icon = Icons.Default.PendingActions,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                CompactStatCard(
                    label = "Pronto",
                    value = "8",
                    icon = Icons.Default.CheckCircle,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                CompactStatCard(
                    label = "Kliente",
                    value = "142",
                    icon = Icons.Default.Group,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── 3. QUICK ACTIONS ──────────────────────────────
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionItem(
                    label = "New Order",
                    icon = Icons.Default.AddShoppingCart,
                    modifier = Modifier.weight(1f)
                )
                QuickActionItem(
                    label = "New Client",
                    icon = Icons.Default.PersonAdd,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── 4. REVENUE SUMMARY CARD ───────────────────────
            RevenueCard()

            Spacer(modifier = Modifier.height(32.dp))

            // ── 5. UPCOMING DEADLINES (HORIZONTAL) ────────────
            SectionHeader(title = "Rekolla tuir mai", actionText = "Haree hotu")
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                UpcomingPickupCard(
                    client = "Abinda Carmo",
                    cake = "Wedding 3-Tier",
                    daysLeft = "Tomorrow",
                    isUrgent = true
                )
                UpcomingPickupCard(
                    client = "Chrismerry",
                    cake = "Birthday Custom",
                    daysLeft = "In 2 days",
                    isUrgent = false
                )
                UpcomingPickupCard(
                    client = "Joana Rosa",
                    cake = "Anniversary Set",
                    daysLeft = "In 5 days",
                    isUrgent = false
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── 6. TODAY'S TIMELINE ───────────────────────────
            SectionHeader(title = "Schedule Ohin", actionText = "Calendar")
            Spacer(modifier = Modifier.height(16.dp))
            
            TimelineItem(
                time = "09:00 AM",
                client = "Eleanor Shellstrop",
                cake = "Caramel Macaron Tower",
                status = "Pronto",
                isLast = false
            )
            TimelineItem(
                time = "02:30 PM",
                client = "Jason Mendoza",
                cake = "6x Jalapeno Cornbread",
                status = "Pending",
                isLast = true
            )
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
fun RevenueCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 1.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Background decoration
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
                    text = "Total Revenue",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$1,248.50",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = null,
                        tint = Color(0xFF4D662A),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "+12% husi semana kotuk",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF4D662A)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickActionItem(label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    ElevatedButton(
        onClick = { /* TODO */ },
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
            // Deadline Badge
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
                text = time.split(" ")[0],
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = time.split(" ")[1],
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

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen()
    }
}
