package com.example.ordermanagementcake.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.entities.OrderStatus
import com.example.ordermanagementcake.data.local.relations.OrderWithCakes
import com.example.ordermanagementcake.data.repository.ClientRepository
import com.example.ordermanagementcake.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

private data class AggData(
    val pending: Int,
    val ready: Int,
    val completed: Int,
    val revenue: Double,
    val clients: Int
)

private data class ListData(
    val todayOrders: List<OrderWithCakes>,
    val upcoming: List<OrderWithCakes>,
    val allClients: List<ClientEntity>
)

class DashboardViewModel(
    private val orderRepository: OrderRepository,
    private val clientRepository: ClientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val today = LocalDate.now().toString()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            combine(
                combine(
                    orderRepository.countOrdersByStatus(OrderStatus.PENDING),
                    orderRepository.countOrdersByStatus(OrderStatus.READY),
                    orderRepository.countOrdersByStatus(OrderStatus.COMPLETED),
                    orderRepository.getTotalRevenue(),
                    clientRepository.countClients()
                ) { pending, ready, completed, revenue, clients ->
                    AggData(pending, ready, completed, revenue, clients)
                },
                combine(
                    orderRepository.getOrdersWithCakesByDate(today),
                    orderRepository.getUpcomingOrdersWithCakes(today),
                    clientRepository.getAllClients()
                ) { todayOrders, upcoming, allClients ->
                    ListData(todayOrders, upcoming, allClients)
                }
            ) { agg, lst ->
                val clientMap = lst.allClients.associateBy { it.id }

                val timelineItems = lst.todayOrders.map { owc ->
                    val order = owc.orders
                    val client = clientMap[order.customerId]
                    val cake = owc.cakes.firstOrNull()
                    TimelineItem(
                        time = order.deliveryDate.take(10),
                        client = client?.name ?: "Kliente #${order.customerId}",
                        cake = cake?.cakeTitle ?: "Bolo ida",
                        status = statusDisplay(order.status.name)
                    )
                }

                val pickupItems = lst.upcoming.map { owc ->
                    val order = owc.orders
                    val client = clientMap[order.customerId]
                    val cake = owc.cakes.firstOrNull()
                    val delivery = parseDate(order.deliveryDate)
                    val days = delivery?.let { d ->
                        val diff = ChronoUnit.DAYS.between(LocalDate.now(), d)
                        when {
                            diff <= 0L -> "Ohin"
                            diff == 1L -> "Aban"
                            diff <= 7L -> "Loron $diff tan"
                            else -> "Semana $diff tan"
                        }
                    } ?: order.deliveryDate

                    PickupItem(
                        client = client?.name ?: "Kliente #${order.customerId}",
                        cake = cake?.cakeTitle ?: "Bolo ida",
                        daysLeft = days,
                        isUrgent = delivery != null &&
                                ChronoUnit.DAYS.between(LocalDate.now(), delivery) <= 1
                    )
                }

                DashboardUiState(
                    todayOrderCount = lst.todayOrders.size,
                    pendingCount = agg.pending,
                    readyCount = agg.ready,
                    completedCount = agg.completed,
                    totalClients = agg.clients,
                    totalRevenue = agg.revenue,
                    timelineItems = timelineItems,
                    pickupItems = pickupItems,
                    isLoading = false
                )
            }
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { state ->
                    _uiState.value = state
                }
        }
    }

    private fun parseDate(date: String): LocalDate? = runCatching {
        LocalDate.parse(date.take(10))
    }.getOrNull()

    private fun statusDisplay(status: String): String = when (status) {
        "PENDING" -> "Hein-hela"
        "IN_PROGRESS" -> "Servisu"
        "READY" -> "Pronto"
        "COMPLETED" -> "Kompletu"
        "CANCELLED" -> "Kansela"
        else -> status
    }
}
