package com.example.ordermanagementcake.data.local.dao

import androidx.room.*
import com.example.ordermanagementcake.data.local.entities.ShapeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShapeDao {

    @Query("SELECT * FROM shapes")
    fun getAllShapes(): Flow<List<ShapeEntity>>

    @Query("SELECT * FROM shapes WHERE shape_id = :id")
    suspend fun getShapeById(id: Int): ShapeEntity?

    @Insert
    suspend fun insertShape(shape: ShapeEntity): Long

    @Update
    suspend fun updateShape(shape: ShapeEntity)

    @Delete
    suspend fun deleteShape(shape: ShapeEntity)
}