package com.example.ordermanagementcake.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.CakeEntity

data class OrderWithCakesAndTiers(
    @Embedded val order: OrderEntity,
    @Relation(
        entity = CakeEntity::class,       // ← needed for nested relations
        parentColumn = "order_id",
        entityColumn = "order_id"
    )
    val cakes: List<CakeWithTiers>        // ← each cake already has its tiers inside
)