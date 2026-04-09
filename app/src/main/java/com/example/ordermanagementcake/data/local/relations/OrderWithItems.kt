package com.example.ordermanagementcake.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderItemEntity

// Ida n'e dta class id ane'be mak jion order ho nia items sira
data class OrderWithItems(
    @Embedded val order: OrderEntity, // @Embede ne brak object OrderEntity sai  koluna idaidak
    @Relation(
        parentColumn = "id", //OrderEntity.id, (PK)
        entityColumn = "orderId" // OrderItemsEntity.orderId (FK)
    )
    val items: List<OrderItemEntity> // OorderItem sira ne'eb relasiona ho Orders sei rai iha ne'e. Line ida n'ee mos hatete katak ordersnia child entity mak OrderItemEntity
)