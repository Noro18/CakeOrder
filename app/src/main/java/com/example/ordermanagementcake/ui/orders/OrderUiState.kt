package com.example.ordermanagementcake.ui.orders

import com.example.ordermanagementcake.data.local.entities.OrderEntity

data class OrderUiState (
    val orders: List<OrderEntity> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedStatus: String = "PENDING"
)