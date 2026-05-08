package com.example.ordermanagementcake.data.repository

import com.example.ordermanagementcake.data.local.dao.TierDao
import com.example.ordermanagementcake.data.local.entities.TierEntity
import kotlinx.coroutines.flow.Flow

class TierRepository(private val tierDao: TierDao) {

    fun getTiersForCake(cakeId: Int): Flow<List<TierEntity>> =
        tierDao.getTiersForCake(cakeId)

    suspend fun insertTier(tier: TierEntity): Long =
        tierDao.insertTier(tier)

    suspend fun insertTiers(tiers: List<TierEntity>) =
        tierDao.insertTiers(tiers)

    suspend fun updateTier(tier: TierEntity) =
        tierDao.updateTier(tier)

    suspend fun deleteTier(tier: TierEntity) =
        tierDao.deleteTier(tier)

    suspend fun deleteTiersForCake(cakeId: Int) =
        tierDao.deleteTiersForCake(cakeId)
}