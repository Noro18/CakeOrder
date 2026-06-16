package com.example.ordermanagementcake.data.repository

import com.example.ordermanagementcake.data.local.dao.OrderDao
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderStatus
import com.example.ordermanagementcake.data.local.relations.OrderFullDetail
import com.example.ordermanagementcake.data.local.relations.OrderWithCakeAndTiers
import com.example.ordermanagementcake.data.local.relations.OrderWithCakes
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {

    // Updated to return the relationship so we can see the Cake titles
    fun getOrdersWithCakesByStatus(status: OrderStatus): Flow<List<OrderWithCakes>> =
        orderDao.getOrdersWithCakesByStatus(status)

    fun getAllOrders(): Flow<List<OrderEntity>> =
        orderDao.getAllOrders()

    fun getOrderWithCakesAndTiers(id: Int): Flow<OrderWithCakeAndTiers?> =
        orderDao.getOrderWithCakesAndTiers(id)

    fun getOrderFullDetail(id: Int): Flow<OrderFullDetail?> =
        orderDao.getOrderFullDetail(id)

    suspend fun insertOrder(order: OrderEntity): Long =
        orderDao.insertOrder(order)

    suspend fun updateStatus(orderId: Int, status: OrderStatus) =
        orderDao.updateStatus(orderId, status)

    suspend fun deleteOrder(order: OrderEntity) =
        orderDao.deleteOrder(order)

    fun getOrdersByMonth(monthPrefix: String): Flow<List<OrderEntity>> =
        orderDao.getOrdersByMonth(monthPrefix)

    fun getOrdersByDate(datePrefix: String): Flow<List<OrderEntity>> =
        orderDao.getOrdersByDate(datePrefix)
}
