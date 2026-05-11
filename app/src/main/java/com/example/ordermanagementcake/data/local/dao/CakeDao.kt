package com.example.ordermanagementcake.data.local.dao

import androidx.room.*
import com.example.ordermanagementcake.data.local.entities.CakeEntity
import com.example.ordermanagementcake.data.local.relations.CakeWithTiers
import kotlinx.coroutines.flow.Flow

@Dao
interface CakeDao {

    @Query("SELECT * FROM cakes WHERE order_id = :orderId")
    fun getCakesForOrder(orderId: Int): Flow<List<CakeEntity>>

    @Transaction
    @Query("SELECT * FROM cakes WHERE cake_id = :id")
    fun getCakeWithTiers(id: Int): Flow<CakeWithTiers?>

    @Insert
    suspend fun insertCake(cake: CakeEntity): Long

    @Insert
    suspend fun insertCakes(cakes: List<CakeEntity>)

    @Update
    suspend fun updateCake(cake: CakeEntity)

    @Delete
    suspend fun deleteCake(cake: CakeEntity)

    @Query("DELETE FROM cakes WHERE order_id = :orderId")
    suspend fun deleteCakesForOrder(orderId: Int)
}