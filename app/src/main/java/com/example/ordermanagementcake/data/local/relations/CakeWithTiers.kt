package com.example.ordermanagementcake.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ordermanagementcake.data.local.entities.CakeEntity
import com.example.ordermanagementcake.data.local.entities.TierEntity

data class CakeWithTiers(
    @Embedded val cake: CakeEntity,
    @Relation(
        parentColumn = "cake_id",
        entityColumn = "cake_id"
    )
    val tiers: List<TierEntity> // LIst of tier entity objecct itmes
)
