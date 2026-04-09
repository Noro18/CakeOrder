package com.example.ordermanagementcake.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="orders")
data class OrderEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clientId: Int,
    val orderDate: String,
    val pickupDate: String,
    val status: String,
    val notes: String,
    val createdAt: String
)