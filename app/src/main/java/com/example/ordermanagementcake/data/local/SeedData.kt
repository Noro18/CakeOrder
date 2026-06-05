package com.example.ordermanagementcake.data.local

import com.example.ordermanagementcake.data.local.entities.*

suspend fun seedDatabase(database: OrderDatabase) {
    val clientDao = database.clientDao()
    val orderDao = database.orderDao()
    val cakeDao = database.cakeDao()
    val tierDao = database.tierDao()
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

    // 4. Seed Clients
    // 3. Seed Clients
    val now = System.currentTimeMillis()
    val clientIds = listOf(
        ClientEntity(name = "Eleanor Rigby", phone = "+670 77100001", address = "Dili, Timor-Leste", createdAt = now - 1000 * 60 * 60 * 24 * 4), // 4 days ago
        ClientEntity(name = "Arthur Dent", phone = "+670 77100002", address = "Baucau, Timor-Leste", createdAt = now - 1000 * 60 * 60 * 24 * 3), // 3 days ago
        ClientEntity(name = "Sarah Jenkins", phone = "+670 77100003", address = "Dili, Timor-Leste", createdAt = now - 1000 * 60 * 60 * 24 * 2), // 2 days ago
        ClientEntity(name = "Michael Scott", phone = "+670 77100004", address = "Dili, Timor-Leste", createdAt = now) // now
    ).map { clientDao.insertClient(it) }

    // 4. Seed Orders
    val orderIds = listOf(
        OrderEntity(
            customerId = clientIds[0].toInt(),
            orderDate = "2026-05-10",
            deliveryDate = "2026-05-24 14:00",
            status = OrderStatus.PENDING,
            orderNotes = "Extra fudge on top",
            totalPrice = 45.0
        ),
        OrderEntity(
            customerId = clientIds[1].toInt(),
            orderDate = "2026-05-10",
            deliveryDate = "2026-05-24 16:30",
            status = OrderStatus.PENDING,
            orderNotes = "Allergy: no nuts",
            totalPrice = 35.0
        ),
        OrderEntity(
            customerId = clientIds[2].toInt(),
            orderDate = "2026-05-11",
            deliveryDate = "2026-05-28 10:00",
            status = OrderStatus.IN_PROGRESS,
            orderNotes = "Write Happy Birthday Ana",
            totalPrice = 120.0
        ),
        OrderEntity(
            customerId = clientIds[3].toInt(),
            orderDate = "2026-05-08",
            deliveryDate = "2026-05-20 09:00",
            status = OrderStatus.COMPLETED,
            orderNotes = "Grand wedding cake",
            totalPrice = 500.0
        )
    ).map { orderDao.insertOrder(it) }

    // 5. Seed Cakes and Tiers

    // Order 0: Double Chocolate Fudge (1 tier)
    val cake1Id = cakeDao.insertCake(
        CakeEntity(
            orderId = orderIds[0].toInt(),
            cakeTitle = "Double Chocolate Fudge",
            cakeNotes = "8 inch, dark chocolate ganache",
            imageUri = "double_chodolate_fudge"
        )
    )
    tierDao.insertTier(
        TierEntity(
            cakeId = cake1Id.toInt(),
            level = 1,
            shapeId = shapeCircle.toInt(),
            sizeId = size8.toInt(),
            colorHex = "#3E2723",
            price = 45.0
        )
    )

    // Order 1: Wild Berry Medley (1 tier)
    val cake2Id = cakeDao.insertCake(
        CakeEntity(
            orderId = orderIds[1].toInt(),
            cakeTitle = "Wild Berry Medley",
            cakeNotes = "6 inch, mixed berry compote",
            imageUri = null
        )
    )
    tierDao.insertTier(
        TierEntity(
            cakeId = cake2Id.toInt(),
            level = 1,
            shapeId = shapeCircle.toInt(),
            sizeId = size6.toInt(),
            colorHex = "#AD1457",
            price = 35.0
        )
    )

    // Order 2: Zesty Lemon Celebration (2 tiers)
    val cake3Id = cakeDao.insertCake(
        CakeEntity(
            orderId = orderIds[2].toInt(),
            cakeTitle = "Zesty Lemon Celebration",
            cakeNotes = "Two tier lemon cake",
            imageUri = null
        )
    )
    tierDao.insertTiers(
        listOf(
            TierEntity(
                cakeId = cake3Id.toInt(),
                level = 1,
                shapeId = shapeCircle.toInt(),
                sizeId = size10.toInt(),
                colorHex = "#FFF176",
                price = 70.0
            ),
            TierEntity(
                cakeId = cake3Id.toInt(),
                level = 2,
                shapeId = shapeCircle.toInt(),
                sizeId = size6.toInt(),
                colorHex = "#FFF9C4",
                price = 50.0
            )
        )
    )

    // Order 3: Grand Wedding Tiered Cake (3 tiers)
    val cake4Id = cakeDao.insertCake(
        CakeEntity(
            orderId = orderIds[3].toInt(),
            cakeTitle = "Grand Wedding Tiered Cake",
            cakeNotes = "White fondant with lace details",
            imageUri = null
        )
    )
    tierDao.insertTiers(
        listOf(
            TierEntity(
                cakeId = cake4Id.toInt(),
                level = 1,
                shapeId = shapeSquare.toInt(),
                sizeId = size12.toInt(),
                colorHex = "#FFFFFF",
                price = 200.0
            ),
            TierEntity(
                cakeId = cake4Id.toInt(),
                level = 2,
                shapeId = shapeCircle.toInt(),
                sizeId = size10.toInt(),
                colorHex = "#FFFFFF",
                price = 180.0
            ),
            TierEntity(
                cakeId = cake4Id.toInt(),
                level = 3,
                shapeId = shapeCircle.toInt(),
                sizeId = size8.toInt(),
                colorHex = "#FFFFFF",
                price = 120.0
            )
        )
    )
}