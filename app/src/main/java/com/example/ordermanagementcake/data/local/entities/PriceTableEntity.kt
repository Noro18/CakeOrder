package com.example.ordermanagementcake.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "price_table",
    foreignKeys = [
        ForeignKey(
            entity = ShapeEntity::class,
            parentColumns = ["shape_id"],
            childColumns = ["shape_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SizeEntity::class,
            parentColumns = ["size_id"],
            childColumns = ["size_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("shape_id"), Index("size_id")]
)
data class PriceTableEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "price_id") val id: Int = 0,
    @ColumnInfo(name = "shape_id") val shapeId: Int,
    @ColumnInfo(name = "size_id") val sizeId: Int,
    @ColumnInfo(name = "price") val price: Double
)