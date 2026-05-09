package com.example.ordermanagementcake.data.repository

import com.example.ordermanagementcake.data.local.dao.ShapeDao
import com.example.ordermanagementcake.data.local.entities.ShapeEntity
import kotlinx.coroutines.flow.Flow

class ShapeRepository(private val shapeDao: ShapeDao) {

    fun getAllShapes(): Flow<List<ShapeEntity>> =
        shapeDao.getAllShapes()

    suspend fun getShapeById(id: Int): ShapeEntity? =
        shapeDao.getShapeById(id)

    suspend fun insertShape(shape: ShapeEntity): Long =
        shapeDao.insertShape(shape)

    suspend fun updateShape(shape: ShapeEntity) =
        shapeDao.updateShape(shape)

    suspend fun deleteShape(shape: ShapeEntity) =
        shapeDao.deleteShape(shape)
}