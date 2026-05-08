package com.example.ordermanagementcake.data.repository

import com.example.ordermanagementcake.data.local.dao.OrderDao
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderStatus
import com.example.ordermanagementcake.data.local.relations.OrderWithCakeAndTiers
import com.example.ordermanagementcake.data.local.relations.OrderWithCakes
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {

    fun getAllOrders(): Flow<List<OrderEntity>> =
        orderDao.getAllOrders()

    fun getOrdersByClient(clientId: Int): Flow<List<OrderEntity>> =
        orderDao.getOrdersByClient(clientId)

    fun getOrdersByStatus(status: OrderStatus): Flow<List<OrderEntity>> =
        orderDao.getOrdersByStatus(status)

    fun getOrderWithCakes(id: Int): Flow<OrderWithCakes?> =
        orderDao.getOrderWithCakes(id)

    fun getOrderWithCakesAndTiers(id: Int): Flow<OrderWithCakeAndTiers?> =
        orderDao.getOrderWithCakesAndTiers(id)

    suspend fun insertOrder(order: OrderEntity): Long =
        orderDao.insertOrder(order)

    suspend fun updateOrder(order: OrderEntity) =
        orderDao.updateOrder(order)

    suspend fun updateStatus(orderId: Int, status: OrderStatus) =
        orderDao.updateStatus(orderId, status)

    suspend fun deleteOrder(order: OrderEntity) =
        orderDao.deleteOrder(order)
}