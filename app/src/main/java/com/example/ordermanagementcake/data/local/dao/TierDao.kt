package com.example.ordermanagementcake.data.local.dao

import androidx.room.*
import com.example.ordermanagementcake.data.local.entities.TierEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TierDao {

    @Query("SELECT * FROM tiers WHERE cake_id = :cakeId ORDER BY level ASC")
    fun getTiersForCake(cakeId: Int): Flow<List<TierEntity>>

    @Insert
    suspend fun insertTier(tier: TierEntity): Long

    @Insert
    suspend fun insertTiers(tiers: List<TierEntity>)

    @Update
    suspend fun updateTier(tier: TierEntity)

    @Delete
    suspend fun deleteTier(tier: TierEntity)

    @Query("DELETE FROM tiers WHERE cake_id = :cakeId")
    suspend fun deleteTiersForCake(cakeId: Int)
}