package com.example.ordermanagementcake.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.R

@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header Section
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            Text(
                text = stringResource(id = R.string.greeting),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.datetime_dashboard),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // Stats Grid
        Row(modifier = Modifier.fillMaxWidth()) {
            StatCard(
                label = stringResource(id = R.string.todays_order),
                value = stringResource(id = R.string.num_todays_order),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            StatCard(
                label = stringResource(id = R.string.btn_pending_order),
                value = stringResource(id = R.string.num_pending),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            StatCard(
                label = stringResource(id = R.string.ready_for_pickup),
                value = stringResource(id = R.string.num_ready),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            StatCard(
                label = stringResource(id = R.string.total_client),
                value = stringResource(id = R.string.num_total_clients),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Upcoming Pickups Section
        SectionHeader(
            title = stringResource(id = R.string.upcoming_pickups),
            actionText = stringResource(id = R.string.view_all)
        )
        
        PickupItem()

        Spacer(modifier = Modifier.height(32.dp))

        // Recent Orders Section
        Text(
            text = "Orderan agora dau-daun",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        OrderItem()
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = value, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = label, style = MaterialTheme.typography.labelLarge)
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
            Text(text = actionText, color = Color(0xFF9A2A07))
        }
    }
}

@Composable
fun PickupItem() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.double_chodolate_fudge),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Eleanor Shellstrop", fontWeight = FontWeight.Bold)
                Text(text = "Salted Caramel Macaron Tower", style = MaterialTheme.typography.bodyMedium)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Today", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(text = "09:00 AM", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun OrderItem() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Jason Mendoza", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "6x Jalapeno Cornbread Loaves", style = MaterialTheme.typography.bodySmall)
                Text(text = "Order #8841", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Surface(
                    color = Color(0xFFA9EF95),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Ready",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = Color(0xFF31912E),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$48.00", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
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
