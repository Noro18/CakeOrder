package com.example.ordermanagementcake.ui.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import java.time.format.TextStyle
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import java.util.Locale
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
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

@Composable
fun ScheduleViewScreen(
    year: Int = LocalDate.now().year,        // Default to current year
    month: Int = LocalDate.now().monthValue, // Default to current month (1 = January)
    deliveryCount: Int = 48,
    dotDays: Set<Int> = setOf(3, 5, 11, 14), // Days that have a dot indicator
    onDayClick: (Int) -> Unit = {}
) {
    // Track which day is selected, defaults to today
    var selectedDay by remember { mutableStateOf(LocalDate.now().dayOfMonth) }

    // Track which month/year is currently displayed (can change via nav buttons)
    var currentMonth by remember { mutableStateOf(month) }
    var currentYear by remember { mutableStateOf(year) }

    // Build a YearMonth object to calculate days and layout
    val yearMonth = YearMonth.of(currentYear, currentMonth)

    // Get what day of week the 1st falls on (e.g. MONDAY, TUESDAY, etc.)
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek

    // Calculate how many empty cells to show before day 1 (calendar starts on Monday)
    // e.g. if 1st is Wednesday, offset = 2 (Mon and Tue are empty)
    val offset = (firstDayOfWeek.value - 1) % 7

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(top = 15.dp)
                .padding(horizontal = 20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                // ── Header: month title + delivery count + nav buttons ──
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        // Display full month name and year (e.g. "April 2026")
                        Text(
                            text = "${yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $currentYear",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB85C1A)
                        )
                        // Subtitle showing delivery count for the month
                        Text(
                            text = "$deliveryCount Deliveries scheduled this month",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    }

                    // Previous and next month navigation buttons
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        NavButton("<") {
                            // Go to previous month, roll back year if needed
                            if (currentMonth == 1) {
                                currentMonth = 12
                                currentYear--
                            } else {
                                currentMonth--
                            }
                        }
                        NavButton(">") {
                            // Go to next month, roll forward year if needed
                            if (currentMonth == 12) {
                                currentMonth = 1
                                currentYear++
                            } else {
                                currentMonth++
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // ── Day of week labels (Mon–Sun) ──
                val dayLabels = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
                Row(Modifier.fillMaxWidth()) {
                    dayLabels.forEach { label ->
                        Text(
                            text = label,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                // ── Calendar grid ──
                val totalDays = yearMonth.lengthOfMonth() // e.g. 30 for April, 31 for May

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7), // 7 columns = 7 days in a week
                    modifier = Modifier.heightIn(max = 300.dp),
                    userScrollEnabled = false      // Disable scroll, show all dates at once
                ) {
                    // Empty cells before day 1 to align with correct weekday column
                    items(offset) {
                        Spacer(Modifier.aspectRatio(1f))
                    }

                    // Render each day of the month
                    items(totalDays) { index ->
                        val day = index + 1
                        val isSelected = day == selectedDay
                        val hasDot = day in dotDays // Show dot if this day has an event

                        DayCell(
                            day = day,
                            isSelected = isSelected,
                            hasDot = hasDot,
                            onClick = {
                                selectedDay = day
                                onDayClick(day)
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
            ) {
                Text (
                    text = "Rekolla ba April 16",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text (
                    text = "Saturday . 3 orders total",
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(120.dp) )

            Text(
                text = "View All",
                color = Color(0xFFD76416),
                modifier = Modifier
                    .padding(top = 23.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.foto_profile),
                        contentDescription = "Foto Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Eleanor Vance",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Triple berry chantilly . 2 qty",
                            fontSize = 16.sp
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                    ) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .height(33.dp)

                        ) {
                            Text(
                                text = "Ready",
                                fontSize = 11.sp

                            )
                        }

                        Text(
                            text = "10:30 PM",
                            modifier = Modifier
                                .padding(top = 6.dp, start = 16.dp),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

// ── Single day cell in the calendar grid ──
@Composable
fun DayCell(day: Int, isSelected: Boolean, hasDot: Boolean, onClick: () -> Unit) {
    val orange = Color(0xFFC96A1E)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .aspectRatio(1f) // Keep each cell square
            .padding(2.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Circle background: filled orange if selected, transparent otherwise
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(if (isSelected) orange else Color.Transparent)
                .clickable { onClick() }
        ) {
            Text(
                text = "$day",
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface // <-- ganti ini
            )
        }

        // Dot indicator below the day number
        // - Hidden if no event on this day
        // - White if day is selected (so it shows on orange background)
        // - Orange if day has an event but is not selected
        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(
                    when {
                        !hasDot -> Color.Transparent
                        isSelected -> Color.White
                        else -> orange
                    }
                )
        )
    }
}

// ── Small circular navigation button (< or >) ──
@Composable
fun NavButton(label: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            .clickable { onClick() }
    ) {
        Text(label, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleViewScreenPreview() {
    ScheduleViewScreen()
}