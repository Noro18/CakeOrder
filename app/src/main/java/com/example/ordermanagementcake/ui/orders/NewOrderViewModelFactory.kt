package com.example.ordermanagementcake.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ordermanagementcake.data.repository.CakeRepository
import com.example.ordermanagementcake.data.repository.OrderRepository
import com.example.ordermanagementcake.data.repository.PriceTableRepository
import com.example.ordermanagementcake.data.repository.ShapeRepository
import com.example.ordermanagementcake.data.repository.SizeRepository
import com.example.ordermanagementcake.data.repository.ClientRepository
import com.example.ordermanagementcake.data.repository.TierRepository

class NewOrderViewModelFactory(
    private val orderRepository: OrderRepository,
    private val cakeRepository: CakeRepository,
    private val tierRepository: TierRepository,
    private val shapeRepository: ShapeRepository,
    private val sizeRepository: SizeRepository,
    private val clientRepository: ClientRepository,
    private val priceTableRepository: PriceTableRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewOrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewOrderViewModel(
                orderRepository,
                cakeRepository,
                tierRepository,
                shapeRepository,
                sizeRepository,
                clientRepository,
                priceTableRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

