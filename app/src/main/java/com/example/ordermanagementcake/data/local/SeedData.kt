package com.example.ordermanagementcake.data.local

import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderItemEntity

suspend fun seedDatabase(database: OrderDatabase) {
    val clientDao = database.clientDao()
    val orderDao  = database.orderDao()
    val itemDao   = database.orderItemDao()

    val clientIds = listOf(
        ClientEntity(name = "Eleanor Rigby",    phoneNumber = "+670 77100001", address = "Dili, Timor-Leste",  createdAt = "2026-01-10"),
        ClientEntity(name = "Arthur Dent",      phoneNumber = "+670 77100002", address = "Baucau, Timor-Leste", createdAt = "2026-01-15"),
        ClientEntity(name = "Sarah Jenkins",    phoneNumber = "+670 77100003", address = "Dili, Timor-Leste",  createdAt = "2026-02-01"),
        ClientEntity(name = "Michael Scott",    phoneNumber = "+670 77100004", address = "Dili, Timor-Leste",  createdAt = "2026-02-14"),
        ClientEntity(name = "Abinda Carmo",     phoneNumber = "+670 76534263", address = "Dili, Timor-Leste",  createdAt = "2026-03-01"),
        ClientEntity(name = "Chrismerry Carmo", phoneNumber = "+670 76588263", address = "Dili, Timor-Leste",  createdAt = "2026-03-05")
    ).map { clientDao.insertClient(it) }

    val orderIds = listOf(
        OrderEntity(clientId = clientIds[0].toInt(), orderDate = "2026-04-10", pickupDate = "Oct 24, 14:00", status = "PENDING",     notes = "Extra fudge on top",       createdAt = "2026-04-10"),
        OrderEntity(clientId = clientIds[1].toInt(), orderDate = "2026-04-10", pickupDate = "Oct 24, 16:30", status = "PENDING",     notes = "Allergy: no nuts",         createdAt = "2026-04-10"),
        OrderEntity(clientId = clientIds[2].toInt(), orderDate = "2026-04-11", pickupDate = "Today, 10:00",  status = "IN_PROGRESS", notes = "Write Happy Birthday Ana", createdAt = "2026-04-11"),
        OrderEntity(clientId = clientIds[3].toInt(), orderDate = "2026-04-08", pickupDate = "Yesterday",     status = "COMPLETED",   notes = "5-tier wedding cake",      createdAt = "2026-04-08"),
        OrderEntity(clientId = clientIds[4].toInt(), orderDate = "2026-04-12", pickupDate = "Apr 15, 09:00", status = "READY",       notes = "Box with ribbon please",   createdAt = "2026-04-12"),
        OrderEntity(clientId = clientIds[5].toInt(), orderDate = "2026-04-13", pickupDate = "Apr 16, 11:00", status = "IN_PROGRESS", notes = "Gluten free base",         createdAt = "2026-04-13")
    ).map { orderDao.insertOrder(it) }

    listOf(
        OrderItemEntity(orderId = orderIds[0].toInt(), title = "Double Chocolate Fudge", description = "8 inch, dark chocolate ganache", imageRef = "double_chodolate_fudge", quantity = 1,  notes = "Extra fudge on top"),
        OrderItemEntity(orderId = orderIds[1].toInt(), title = "Wild Berry Medley",       description = "6 inch, mixed berry compote",    imageRef = "wild_berry",              quantity = 2,  notes = "No nuts"),
        OrderItemEntity(orderId = orderIds[2].toInt(), title = "Zesty Lemon Drizzle",     description = "6 inch, lemon curd filling",     imageRef = "lemon_drizzle",           quantity = 1,  notes = "Happy Birthday Ana"),
        OrderItemEntity(orderId = orderIds[3].toInt(), title = "Custom Wedding Tier",     description = "5 tiers, white fondant",         imageRef = "wedding_cake",            quantity = 1,  notes = ""),
        OrderItemEntity(orderId = orderIds[4].toInt(), title = "Salted Caramel Macaron",  description = "Tower of 24 macarons",           imageRef = "macaron_tower",           quantity = 1,  notes = "Ribbon box"),
        OrderItemEntity(orderId = orderIds[4].toInt(), title = "Vanilla Cupcakes",        description = "Box of 12, vanilla buttercream", imageRef = "vanilla_cupcakes",        quantity = 12, notes = ""),
        OrderItemEntity(orderId = orderIds[5].toInt(), title = "Gluten Free Carrot Cake", description = "8 inch, cream cheese frosting",  imageRef = "carrot_cake",             quantity = 1,  notes = "Gluten free base")
    ).forEach { itemDao.insertItem(it) }
}