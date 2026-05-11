package com.example.ordermanagementcake.data.local.dao

import androidx.room.*
import com.example.ordermanagementcake.data.local.entities.PriceTableEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceTableDao {

    @Query("SELECT * FROM price_table")
    fun getAllPrices(): Flow<List<PriceTableEntity>>

    @Query("SELECT * FROM price_table WHERE shape_id = :shapeId AND size_id = :sizeId")
    suspend fun getPrice(shapeId: Int, sizeId: Int): PriceTableEntity?

    @Insert
    suspend fun insertPrice(price: PriceTableEntity): Long

    @Update
    suspend fun updatePrice(price: PriceTableEntity)

    @Delete
    suspend fun deletePrice(price: PriceTableEntity)
}