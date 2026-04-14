package com.example.ordermanagementcake.data.repository

import com.example.ordermanagementcake.data.local.dao.OrderDao
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.relations.OrderWithItems
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {

    fun getOrdersByStatus(status: String): Flow<List<OrderEntity>> =
        orderDao.getOrdersByStatus(status) // funciton iha Repository ne'e atu retorna deit valor husi Dao nian (return orderDao.getORderByStatus(status())

    fun getOrderWithItems(id: Int): Flow<OrderWithItems?> =
        orderDao.getOrderWithItems(id)

    suspend fun insertOrder(order: OrderEntity): Long =
        orderDao.insertOrder(order)

    suspend fun updateOrder(order: OrderEntity) =
        orderDao.updateOrder(order)

    suspend fun updateStatus(orderId: Int, status: String) =
        orderDao.updateStatus(orderId, status)

    suspend fun deleteOrder(order: OrderEntity) =
        orderDao.deleteOrder(order)
}