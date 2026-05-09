package com.example.ordermanagementcake.data.local.dao

import androidx.room.*
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderStatus
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

    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Query("UPDATE orders SET status = :status WHERE order_id = :orderId")
    suspend fun updateStatus(orderId: Int, status: OrderStatus)

    @Delete
    suspend fun deleteOrder(order: OrderEntity)
}