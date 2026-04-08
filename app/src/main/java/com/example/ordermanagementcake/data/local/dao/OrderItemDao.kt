package com.example.ordermanagementcake.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ordermanagementcake.data.local.entities.OrderItemEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface OrderItemDao {
    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getItemsForOrder(orderId: Int): Flow<List<OrderItemEntity>>

    @Insert
    suspend fun insertItem(item: OrderItemEntity)

    @Insert
    suspend fun insertItems(items: List<OrderItemEntity>)

    @Update
    suspend fun updateItem(item: OrderItemEntity)

    @Delete
    suspend fun deleteItem(item: OrderItemEntity)

    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    suspend fun deleteItemsForOrder(orderId: Int)
}