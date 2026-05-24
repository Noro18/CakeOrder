package com.example.ordermanagementcake.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

data class ScheduleUiState(
    val ordersForMonth: List<OrderEntity> = emptyList(),
    val ordersForSelectedDay: List<OrderEntity> = emptyList(),
    val orderDays: Set<LocalDate> = emptySet(), // days that have orders → for dots
    val selectedDate: LocalDate = LocalDate.now(),
    val currentMonth: YearMonth = YearMonth.now(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ScheduleViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    init {
        loadMonth(YearMonth.now())
        loadOrdersForDate(LocalDate.now())
    }

    fun loadMonth(yearMonth: YearMonth) {
        _uiState.update { it.copy(currentMonth = yearMonth) }
        viewModelScope.launch {
            repository.getOrdersByMonth(yearMonth.toString()) // "2026-05"
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = e.message) }
                }
                .collect { orders ->
                    // extract just the LocalDate from each order's deliveryDate string
                    val days = orders.mapNotNull { order ->
                        runCatching {
                            LocalDate.parse(order.deliveryDate.take(10))
                        }.getOrNull()
                    }.toSet()

                    _uiState.update { it.copy(ordersForMonth = orders, orderDays = days) }
                }
        }
    }

    fun loadOrdersForDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        viewModelScope.launch {
            repository.getOrdersByDate(date.toString()) // "2026-05-24"
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = e.message) }
                }
                .collect { orders ->
                    _uiState.update { it.copy(ordersForSelectedDay = orders) }
                }
        }
    }

    fun onDaySelected(date: LocalDate) {
        loadOrdersForDate(date)
    }

    fun onMonthChanged(yearMonth: YearMonth) {
        loadMonth(yearMonth)
    }
}