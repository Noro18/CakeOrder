package com.example.ordermanagementcake.data.local

import com.example.ordermanagementcake.data.local.entities.*

suspend fun seedDatabase(database: OrderDatabase) {
    val shapeDao = database.shapeDao()
    val sizeDao = database.sizeDao()
    val priceTableDao = database.priceTableDao()

    // 1. Seed Shapes
    val shapeCircle = shapeDao.insertShape(ShapeEntity(shapeName = "Circle"))
    val shapeSquare = shapeDao.insertShape(ShapeEntity(shapeName = "Square"))
    val shapeHeart = shapeDao.insertShape(ShapeEntity(shapeName = "Heart"))

    // 2. Seed Sizes
    val size6 = sizeDao.insertSize(SizeEntity(inches = 6.0))
    val size8 = sizeDao.insertSize(SizeEntity(inches = 8.0))
    val size10 = sizeDao.insertSize(SizeEntity(inches = 10.0))
    val size12 = sizeDao.insertSize(SizeEntity(inches = 12.0))

    // 3. Seed Price Table
    priceTableDao.insertPrices(listOf(
        // Circle prices
        PriceTableEntity(shapeId = shapeCircle.toInt(), sizeId = size6.toInt(), price = 30.0),
        PriceTableEntity(shapeId = shapeCircle.toInt(), sizeId = size8.toInt(), price = 45.0),
        PriceTableEntity(shapeId = shapeCircle.toInt(), sizeId = size10.toInt(), price = 60.0),
        PriceTableEntity(shapeId = shapeCircle.toInt(), sizeId = size12.toInt(), price = 80.0),
        // Square prices
        PriceTableEntity(shapeId = shapeSquare.toInt(), sizeId = size6.toInt(), price = 35.0),
        PriceTableEntity(shapeId = shapeSquare.toInt(), sizeId = size8.toInt(), price = 50.0),
        PriceTableEntity(shapeId = shapeSquare.toInt(), sizeId = size10.toInt(), price = 70.0),
        PriceTableEntity(shapeId = shapeSquare.toInt(), sizeId = size12.toInt(), price = 95.0),
        // Heart prices
        PriceTableEntity(shapeId = shapeHeart.toInt(), sizeId = size6.toInt(), price = 40.0),
        PriceTableEntity(shapeId = shapeHeart.toInt(), sizeId = size8.toInt(), price = 55.0),
        PriceTableEntity(shapeId = shapeHeart.toInt(), sizeId = size10.toInt(), price = 75.0),
        PriceTableEntity(shapeId = shapeHeart.toInt(), sizeId = size12.toInt(), price = 100.0)
    ))
}
