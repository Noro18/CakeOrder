package com.example.ordermanagementcake.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ordermanagementcake.data.local.entities.CakeEntity
import com.example.ordermanagementcake.data.local.entities.OrderEntity

class OrderWithCakes (
    @Embedded val orders: OrderEntity,
    @Relation(
        parentColumn = "order_id",
        entityColumn = "order_id"
    )
    val cakes: List<CakeEntity>
)