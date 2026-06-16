package com.example.ordermanagementcake.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderStatus
import com.example.ordermanagementcake.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderUiState()) // private mutable State
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow() // StateFlow, Immutable expose ba iha View

    init {
        loadOrders(OrderStatus.PENDING)
    }

    fun loadOrders(status: OrderStatus) {
        _uiState.update { it.copy(isLoading = it.orders.isEmpty(), selectedStatus = status, errorMessage = null) }
        viewModelScope.launch {
            repository.getOrdersWithCakesByStatus(status)
                .catch { e -> // Erro message
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { ordersWithCakes ->
                    _uiState.update { it.copy(orders = ordersWithCakes, isLoading = false) }
                }
        }
    }

    fun loadOrderDetail(orderId: Int) {
        viewModelScope.launch {
            repository.getOrderFullDetail(orderId)
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = e.message) }
                }
                .collect { detail ->
                    _uiState.update { it.copy(selectedOrderDetail = detail) }
                }
        }
    }

    fun updateStatus(orderId: Int, status: OrderStatus) {
        viewModelScope.launch {
            repository.updateStatus(orderId, status)
        }
    }

    fun deleteOrder(order: OrderEntity) {
        viewModelScope.launch {
            repository.deleteOrder(order)
        }
    }
}
