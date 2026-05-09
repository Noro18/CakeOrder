package com.example.ordermanagementcake.data.repository

import com.example.ordermanagementcake.data.local.dao.SizeDao
import com.example.ordermanagementcake.data.local.entities.SizeEntity
import kotlinx.coroutines.flow.Flow

class SizeRepository(private val sizeDao: SizeDao) {

    fun getAllSizes(): Flow<List<SizeEntity>> =
        sizeDao.getAllSizes()

    suspend fun getSizeById(id: Int): SizeEntity? =
        sizeDao.getSizeById(id)

    suspend fun insertSize(size: SizeEntity): Long =
        sizeDao.insertSize(size)

    suspend fun updateSize(size: SizeEntity) =
        sizeDao.updateSize(size)

    suspend fun deleteSize(size: SizeEntity) =
        sizeDao.deleteSize(size)
}