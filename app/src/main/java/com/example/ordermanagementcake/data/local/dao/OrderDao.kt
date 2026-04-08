package com.example.ordermanagementcake.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.relations.OrderWithItems
import kotlinx.coroutines.flow.Flow


@Dao
interface OrderDao {

    @Query("SELECT * FROM ORDERS WHERE status = :status ORDER BY pickupDate ASC")
    fun getOrdersByStatus(status: String): Flow<List<OrderEntity>>

    @Transaction
    @Query("SELECT * FROM ORDERS WHERE id = :id")
    fun getOrderWithItems(id: Int): Flow<OrderWithItems?>

    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Query("UPDATE orders SET status = :status WHERE id = :orderId")
    suspend fun updateStatus(orderId: Int, status: String)

    @Delete
    suspend fun deleteOrder(order: OrderEntity)



}