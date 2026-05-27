package com.example.ordermanagementcake.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.DayOfWeek
import java.time.format.TextStyle
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderStatus

@Composable
fun ScheduleViewScreen(viewModel: ScheduleViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val brandOrange = Color(0xFFE8640C)
    val config = LocalConfiguration.current
    val locale = config.locales[0]

    val calendarState = rememberSelectableCalendarState(
        initialSelectionMode = SelectionMode.Single
    )

    // When the library's month changes, tell the ViewModel to load that month's orders
    LaunchedEffect(calendarState.monthState.currentMonth) {
        viewModel.onMonthChanged(calendarState.monthState.currentMonth)
    }

    // When the library's selection changes, tell the ViewModel to load that day's orders
    LaunchedEffect(calendarState.selectionState.selection) {
        calendarState.selectionState.selection.firstOrNull()?.let { date ->
            viewModel.onDaySelected(date)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        // ── Header ────────────────────────────────────────────
        item {
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
        }

        // ── Calendar Card ─────────────────────────────────────
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Month count summary
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${uiState.ordersForMonth.size} Deliveries this month",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = brandOrange,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    SelectableCalendar(
                        calendarState = calendarState,
                        dayContent = { dayState ->
                            DayCell(
                                state = dayState,
                                hasOrder = dayState.date in uiState.orderDays,
                                brandOrange = brandOrange
                            )
                        },
                        monthHeader = { monthState ->
                            MonthHeader(
                                monthState = monthState,
                                brandOrange = brandOrange
                            )
                        },
                        daysOfWeekHeader = { daysOfWeek ->
                            WeekHeader(daysOfWeek = daysOfWeek)
                        }
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }

        // ── Selected Day Header ───────────────────────────────
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Entrega ba ${uiState.selectedDate.dayOfWeek.getDisplayName(
                            TextStyle.FULL, locale
                        )}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${uiState.selectedDate.month.getDisplayName(
                            TextStyle.SHORT, locale
                        )} ${uiState.selectedDate.dayOfMonth} · " +
                                "${uiState.ordersForSelectedDay.size} orders total",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        // ── Orders List for Selected Day ──────────────────────
        when {
            uiState.ordersForSelectedDay.isEmpty() -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "La iha entrega ba loron ne'e.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            }
            else -> {
                items(uiState.ordersForSelectedDay) { order ->
                    DeliveryOrderCard(
                        order = order,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

// ── Month Header ──────────────────────────────────────────────────────────────

@Composable
fun MonthHeader(monthState: MonthState, brandOrange: Color) {
    val config = LocalConfiguration.current
    val locale = config.locales[0]
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${monthState.currentMonth.month.getDisplayName(
                TextStyle.FULL, locale
            )} ${monthState.currentMonth.year}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = brandOrange
        )
        Row {
            TextButton(onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) }) {
                Text("‹", style = MaterialTheme.typography.titleLarge, color = brandOrange)
            }
            TextButton(onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }) {
                Text("›", style = MaterialTheme.typography.titleLarge, color = brandOrange)
            }
        }
    }
}

// ── Week Header ───────────────────────────────────────────────────────────────

@Composable
fun WeekHeader(daysOfWeek: List<DayOfWeek>) {
    val config = LocalConfiguration.current
    val locale = config.locales[0]
    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

// ── Day Cell ──────────────────────────────────────────────────────────────────

@Composable
fun DayCell(
    state: DayState<DynamicSelectionState>,
    hasOrder: Boolean,
    brandOrange: Color
) {
    val date = state.date
    val isSelected = state.selectionState.isDateSelected(date)
    val isCurrentMonth = state.isFromCurrentMonth

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
                .clickable { state.selectionState.onDateSelected(date) }
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    isSelected -> Color.White
                    !isCurrentMonth -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
        }
        // Order dot
        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .size(4.dp)
                .clip(CircleShape)
                .background(
                    when {
                        !hasOrder -> Color.Transparent
                        isSelected -> Color.White
                        else -> brandOrange
                    }
                )
        )
    }
}

// ── Delivery Order Card ───────────────────────────────────────────────────────

@Composable
fun DeliveryOrderCard(order: OrderEntity, modifier: Modifier = Modifier) {
    val statusColor = when (order.status) {
        OrderStatus.PENDING     -> Color(0xFFEE8111)
        OrderStatus.IN_PROGRESS -> Color(0xFFF87146)
        OrderStatus.READY       -> Color(0xFF31912E)
        OrderStatus.COMPLETED   -> Color(0xFF3B82F6)
        OrderStatus.CANCELLED   -> Color(0xFF9E9E9E)
    }

    // Extract time from "2026-05-24 14:00" → "14:00"
    val deliveryTime = order.deliveryDate.substringAfter(" ", "")

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Status color strip
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(statusColor)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Order #${order.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = order.orderNotes.ifBlank { "La iha nota" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Surface(
                    color = statusColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = order.status.name.replace("_", " "),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = deliveryTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}