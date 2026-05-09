package com.example.ordermanagementcake.data.local

import com.example.ordermanagementcake.data.local.entities.*

suspend fun seedDatabase(database: OrderDatabase) {
    val clientDao = database.clientDao()
    val orderDao = database.orderDao()
    val cakeDao = database.cakeDao()
    val tierDao = database.tierDao()
    val shapeDao = database.shapeDao()
    val sizeDao = database.sizeDao()

    // 1. Seed Shapes
    val shapeCircle = shapeDao.insertShape(ShapeEntity(shapeName = "Circle"))
    val shapeSquare = shapeDao.insertShape(ShapeEntity(shapeName = "Square"))
    val shapeHeart = shapeDao.insertShape(ShapeEntity(shapeName = "Heart"))

    // 2. Seed Sizes
    val size6 = sizeDao.insertSize(SizeEntity(inches = 6.0))
    val size8 = sizeDao.insertSize(SizeEntity(inches = 8.0))
    val size10 = sizeDao.insertSize(SizeEntity(inches = 10.0))
    val size12 = sizeDao.insertSize(SizeEntity(inches = 12.0))

    // 3. Seed Clients
    val clientIds = listOf(
        ClientEntity(name = "Eleanor Rigby", phone = "+670 77100001", address = "Dili, Timor-Leste"),
        ClientEntity(name = "Arthur Dent", phone = "+670 77100002", address = "Baucau, Timor-Leste"),
        ClientEntity(name = "Sarah Jenkins", phone = "+670 77100003", address = "Dili, Timor-Leste"),
        ClientEntity(name = "Michael Scott", phone = "+670 77100004", address = "Dili, Timor-Leste")
    ).map { clientDao.insertClient(it) }

    // 4. Seed Orders
    val orderIds = listOf(
        OrderEntity(
            customerId = clientIds[0].toInt(),
            orderDate = "2026-04-10",
            deliveryDate = "Oct 24, 14:00",
            status = OrderStatus.PENDING,
            orderNotes = "Extra fudge on top",
            totalPrice = 45.0
        ),
        OrderEntity(
            customerId = clientIds[1].toInt(),
            orderDate = "2026-04-10",
            deliveryDate = "Oct 24, 16:30",
            status = OrderStatus.PENDING,
            orderNotes = "Allergy: no nuts",
            totalPrice = 35.0
        ),
        OrderEntity(
            customerId = clientIds[2].toInt(),
            orderDate = "2026-04-11",
            deliveryDate = "Today, 10:00",
            status = OrderStatus.IN_PROGRESS,
            orderNotes = "Write Happy Birthday Ana",
            totalPrice = 120.0
        ),
        OrderEntity(
            customerId = clientIds[3].toInt(),
            orderDate = "2026-04-08",
            deliveryDate = "Yesterday",
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
            cakeNotes = "8 inch, dark chocolate ganache"
        )
    )
    tierDao.insertTier(
        TierEntity(
            cakeId = cake1Id.toInt(),
            level = 1,
            shapeId = shapeCircle.toInt(),
            sizeId = size8.toInt(),
            colorHex = "#3E2723", // Dark Brown
            price = 45.0
        )
    )

    // Order 1: Wild Berry Medley (1 tier)
    val cake2Id = cakeDao.insertCake(
        CakeEntity(
            orderId = orderIds[1].toInt(),
            cakeTitle = "Wild Berry Medley",
            cakeNotes = "6 inch, mixed berry compote"
        )
    )
    tierDao.insertTier(
        TierEntity(
            cakeId = cake2Id.toInt(),
            level = 1,
            shapeId = shapeCircle.toInt(),
            sizeId = size6.toInt(),
            colorHex = "#AD1457", // Berry Red
            price = 35.0
        )
    )

    // Order 2: Birthday Celebration (2 tiers)
    val cake3Id = cakeDao.insertCake(
        CakeEntity(
            orderId = orderIds[2].toInt(),
            cakeTitle = "Zesty Lemon Celebration",
            cakeNotes = "Two tier lemon cake"
        )
    )
    tierDao.insertTiers(listOf(
        TierEntity(
            cakeId = cake3Id.toInt(),
            level = 1,
            shapeId = shapeCircle.toInt(),
            sizeId = size10.toInt(),
            colorHex = "#FFF176", // Yellow
            price = 70.0
        ),
        TierEntity(
            cakeId = cake3Id.toInt(),
            level = 2,
            shapeId = shapeCircle.toInt(),
            sizeId = size6.toInt(),
            colorHex = "#FFF9C4", // Light Yellow
            price = 50.0
        )
    ))

    // Order 3: Massive Wedding Cake (3 tiers)
    val cake4Id = cakeDao.insertCake(
        CakeEntity(
            orderId = orderIds[3].toInt(),
            cakeTitle = "Grand Wedding Tiered Cake",
            cakeNotes = "White fondant with lace details"
        )
    )
    tierDao.insertTiers(listOf(
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
    ))
}
