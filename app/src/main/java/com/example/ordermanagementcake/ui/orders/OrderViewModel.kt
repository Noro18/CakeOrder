package com.example.ordermanagementcake.ui.orders

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

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    init {
        loadOrders("PENDING")
    }

    fun loadOrders(status: String) {
        _uiState.update { it.copy(isLoading = true, selectedStatus = status, errorMessage = null) }
        viewModelScope.launch {
            repository.getOrdersByStatus(status)
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { orders ->
                    _uiState.update { it.copy(orders = orders, isLoading = false) }
                }
        }
    }

    fun updateStatus(orderId: Int, status: String) {
        viewModelScope.launch { // ne haranarn coroutin jadi nia run iha background  hodi update staus via repoisitry
            repository.updateStatus(orderId, status)
        }
    }

    fun deleteOrder(order: OrderEntity) {
        viewModelScope.launch {
            repository.deleteOrder(order)
        }
    }

    fun insertOrder(order: OrderEntity) {
        viewModelScope.launch {
            repository.insertOrder(order)
        }
    }
}