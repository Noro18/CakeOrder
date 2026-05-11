package com.example.ordermanagementcake.data.repository

import com.example.ordermanagementcake.data.local.dao.PriceTableDao
import com.example.ordermanagementcake.data.local.entities.PriceTableEntity
import kotlinx.coroutines.flow.Flow

class PriceTableRepository(private val priceTableDao: PriceTableDao) {

    fun getAllPrices(): Flow<List<PriceTableEntity>> =
        priceTableDao.getAllPrices()

    suspend fun getPrice(shapeId: Int, sizeId: Int): PriceTableEntity? =
        priceTableDao.getPrice(shapeId, sizeId)

    suspend fun insertPrice(price: PriceTableEntity): Long =
        priceTableDao.insertPrice(price)

    suspend fun updatePrice(price: PriceTableEntity) =
        priceTableDao.updatePrice(price)

    suspend fun deletePrice(price: PriceTableEntity) =
        priceTableDao.deletePrice(price)
}