package com.example.ordermanagementcake.ui.orders

import com.example.ordermanagementcake.data.local.entities.OrderStatus
import com.example.ordermanagementcake.data.local.relations.OrderFullDetail
import com.example.ordermanagementcake.data.local.relations.OrderWithCakes

data class OrderUiState (
    val orders: List<OrderWithCakes> = emptyList(),
    val selectedOrderDetail: OrderFullDetail? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedStatus: OrderStatus = OrderStatus.PENDING
)