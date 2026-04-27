package com.example.ordermanagementcake.ui.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.R
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ScheduleViewScreen(
    year: Int = LocalDate.now().year,
    month: Int = LocalDate.now().monthValue,
    deliveryCount: Int = 48,
    dotDays: Set<Int> = setOf(3, 5, 11, 14),
    onDayClick: (Int) -> Unit = {}
) {
    var selectedDay by remember { mutableStateOf(LocalDate.now().dayOfMonth) }
    var currentMonth by remember { mutableStateOf(month) }
    var currentYear by remember { mutableStateOf(year) }

    val yearMonth = YearMonth.of(currentYear, currentMonth)
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek
    val offset = (firstDayOfWeek.value - 1) % 7
    val totalDays = yearMonth.lengthOfMonth()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // --- Header Section ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Kalenáriu Entrega",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Monitoriza ita-nia oráriu entrega hotu",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // --- Calendar Card ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Month & Nav
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $currentYear",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE8640C)
                        )
                        Text(
                            text = "$deliveryCount Deliveries this month",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(onClick = {
                            if (currentMonth == 1) { currentMonth = 12; currentYear-- } else currentMonth--
                        }) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
                        }
                        IconButton(onClick = {
                            if (currentMonth == 12) { currentMonth = 1; currentYear++ } else currentMonth++
                        }) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Day Labels
                val dayLabels = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
                Row(Modifier.fillMaxWidth()) {
                    dayLabels.forEach { label ->
                        Text(
                            text = label,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Calendar Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.heightIn(max = 280.dp),
                    userScrollEnabled = false
                ) {
                    items(offset) { Spacer(Modifier.aspectRatio(1f)) }
                    items(totalDays) { index ->
                        val day = index + 1
                        DayCell(
                            day = day,
                            isSelected = day == selectedDay,
                            hasDot = day in dotDays,
                            onClick = {
                                selectedDay = day
                                onDayClick(day)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Deliveries for Selected Day Section ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Entrega ba ${yearMonth.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} $selectedDay",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Sabadu . 3 orders total",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            TextButton(onClick = { /* TODO */ }) {
                Text(text = "View All", color = Color(0xFFE8640C))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- Delivery Items List ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DeliveryItemCard(
                name = "Eleanor Vance",
                orderDesc = "Triple berry chantilly . 2 qty",
                time = "10:30 PM",
                status = "Ready"
            )
            DeliveryItemCard(
                name = "Jason Mendoza",
                orderDesc = "Chocolate Fudge Cake . 1 qty",
                time = "02:00 PM",
                status = "Pending"
            )
        }
    }
}

@Composable
fun DayCell(day: Int, isSelected: Boolean, hasDot: Boolean, onClick: () -> Unit) {
    val brandOrange = Color(0xFFE8640C)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(if (isSelected) brandOrange else Color.Transparent)
                .clickable { onClick() }
        ) {
            Text(
                text = "$day",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .size(4.dp)
                .clip(CircleShape)
                .background(
                    when {
                        !hasDot -> Color.Transparent
                        isSelected -> Color.White
                        else -> brandOrange
                    }
                )
        )
    }
}

@Composable
fun DeliveryItemCard(name: String, orderDesc: String, time: String, status: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.foto_profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = orderDesc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Surface(
                    color = if (status == "Ready") Color(0xFFA9EF95) else Color(0xFFFFF3E0),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = if (status == "Ready") Color(0xFF31912E) else Color(0xFFE8640C),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleViewScreenPreview() {
    MaterialTheme {
        ScheduleViewScreen()
    }
}
