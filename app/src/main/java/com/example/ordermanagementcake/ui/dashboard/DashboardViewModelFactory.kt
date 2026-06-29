package com.example.ordermanagementcake.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ordermanagementcake.data.repository.ClientRepository
import com.example.ordermanagementcake.data.repository.OrderRepository

class DashboardViewModelFactory(
    private val orderRepository: OrderRepository,
    private val clientRepository: ClientRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(orderRepository, clientRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
