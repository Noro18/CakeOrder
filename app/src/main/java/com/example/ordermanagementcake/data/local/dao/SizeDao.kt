package com.example.ordermanagementcake.data.local.dao

import androidx.room.*
import com.example.ordermanagementcake.data.local.entities.SizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SizeDao {

    @Query("SELECT * FROM sizes")
    fun getAllSizes(): Flow<List<SizeEntity>>

    @Query("SELECT * FROM sizes WHERE size_id = :id")
    suspend fun getSizeById(id: Int): SizeEntity?

    @Insert
    suspend fun insertSize(size: SizeEntity): Long

    @Update
    suspend fun updateSize(size: SizeEntity)

    @Delete
    suspend fun deleteSize(size: SizeEntity)
}