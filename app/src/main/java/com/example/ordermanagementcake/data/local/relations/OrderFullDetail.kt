package com.example.ordermanagementcake.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ordermanagementcake.data.local.entities.CakeEntity
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.entities.OrderEntity

data class OrderFullDetail(
    @Embedded val order: OrderEntity,
    
    @Relation(
        entity = ClientEntity::class,
        parentColumn = "client_id",
        entityColumn = "client_id"
    )
    val client: ClientEntity,

    @Relation(
        entity = CakeEntity::class,
        parentColumn = "order_id",
        entityColumn = "order_id"
    )
    val cakes: List<CakeWithTiers>
)
