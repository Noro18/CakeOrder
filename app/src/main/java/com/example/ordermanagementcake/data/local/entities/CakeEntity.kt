package com.example.ordermanagementcake.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cakes",
    foreignKeys = [ForeignKey(
        entity = OrderEntity::class,
        parentColumns = ["order_id"],
        childColumns = ["order_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["order_id"])]

)
data class CakeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cake_id") val id: Int = 0,
    @ColumnInfo(name = "order_id") val orderId: Int,
    @ColumnInfo(name = "cake_title") val cakeTitle: String,
    @ColumnInfo(name = "cake_notes") val cakeNotes: String,
    @ColumnInfo(name="image)=_uri") val imageUri: String? = null
)
