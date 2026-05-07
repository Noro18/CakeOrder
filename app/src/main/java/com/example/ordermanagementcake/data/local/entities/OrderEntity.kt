package com.example.ordermanagementcake.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName="orders",
    foreignKeys = [ForeignKey(
        entity = ClientEntity::class,
        parentColumns = ["client_id"],
        childColumns = ["client_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("customer_id")]
)
data class OrderEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id") val id: Int = 0,
    @ColumnInfo(name = "customer_id") val customerId: Int,
    @ColumnInfo(name = "order_date") val orderDate: String,
    @ColumnInfo(name = "delivery_date") val deliveryDate: String,
    @ColumnInfo(name = "total_price") val totalPrice: Double,
    @ColumnInfo(name = "order_notes") val orderNotes: String
)