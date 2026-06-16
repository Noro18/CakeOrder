package com.example.ordermanagementcake.data.local.dao

import androidx.room.*
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderStatus
import com.example.ordermanagementcake.data.local.relations.OrderFullDetail
import com.example.ordermanagementcake.data.local.relations.OrderWithCakeAndTiers
import com.example.ordermanagementcake.data.local.relations.OrderWithCakes
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE client_id = :clientId")
    fun getOrdersByClient(clientId: Int): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE status = :status")
    fun getOrdersByStatus(status: OrderStatus): Flow<List<OrderEntity>>

    @Transaction
    @Query("SELECT * FROM orders WHERE status = :status")
    fun getOrdersWithCakesByStatus(status: OrderStatus): Flow<List<OrderWithCakes>>

    @Transaction
    @Query("SELECT * FROM orders WHERE order_id = :id")
    fun getOrderWithCakes(id: Int): Flow<OrderWithCakes?>

    @Transaction
    @Query("SELECT * FROM orders WHERE order_id = :id")
    fun getOrderWithCakesAndTiers(id: Int): Flow<OrderWithCakeAndTiers?>

    @Transaction
    @Query("SELECT * FROM orders WHERE order_id = :id")
    fun getOrderFullDetail(id: Int): Flow<OrderFullDetail?>

    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Query("UPDATE orders SET status = :status WHERE order_id = :orderId")
    suspend fun updateStatus(orderId: Int, status: OrderStatus)

    @Delete
    suspend fun deleteOrder(order: OrderEntity)

    // 1. For the calendar dots — fetches all orders in a month e.g. "2026-05"
    @Query("SELECT * FROM orders WHERE delivery_date LIKE :monthPrefix || '%'")
    fun getOrdersByMonth(monthPrefix: String): Flow<List<OrderEntity>>

    // 2. For the list below — fetches orders for a specific tapped day e.g. "2026-05-24"
    @Query("SELECT * FROM orders WHERE delivery_date LIKE :datePrefix || '%'")
    fun getOrdersByDate(datePrefix: String): Flow<List<OrderEntity>>
}