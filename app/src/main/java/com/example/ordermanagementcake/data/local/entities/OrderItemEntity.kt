package com.example.ordermanagementcake.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: Int,
    val title: String,
    val description: String,
    val imageRef: String,
    val quantity: Int,
    val notes: String
)
