package com.example.ordermanagementcake.data.repository

import com.example.ordermanagementcake.data.local.dao.CakeDao
import com.example.ordermanagementcake.data.local.entities.CakeEntity
import com.example.ordermanagementcake.data.local.relations.CakeWithTiers
import kotlinx.coroutines.flow.Flow

class CakeRepository(private val cakeDao: CakeDao) {

    fun getCakesForOrder(orderId: Int): Flow<List<CakeEntity>> =
        cakeDao.getCakesForOrder(orderId)

    fun getCakeWithTiers(id: Int): Flow<CakeWithTiers?> =
        cakeDao.getCakeWithTiers(id)

    suspend fun insertCake(cake: CakeEntity): Long =
        cakeDao.insertCake(cake)

    suspend fun insertCakes(cakes: List<CakeEntity>) =
        cakeDao.insertCakes(cakes)

    suspend fun updateCake(cake: CakeEntity) =
        cakeDao.updateCake(cake)

    suspend fun deleteCake(cake: CakeEntity) =
        cakeDao.deleteCake(cake)

    suspend fun deleteCakesForOrder(orderId: Int) =
        cakeDao.deleteCakesForOrder(orderId)
}