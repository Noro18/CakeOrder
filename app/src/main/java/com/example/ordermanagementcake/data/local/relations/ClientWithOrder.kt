package com.example.ordermanagementcake.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.entities.OrderEntity

data class ClientWithOrder(
    @Embedded val client: ClientEntity,
    @Relation (
        parentColumn = "id",
        entityColumn = "clientId"
    )
    val orders: List<OrderEntity>
)
