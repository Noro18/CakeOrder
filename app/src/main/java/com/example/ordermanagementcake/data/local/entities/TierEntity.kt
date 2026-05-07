package com.example.ordermanagementcake.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tiers",
    foreignKeys = [
        ForeignKey(
            entity = CakeEntity::class,
            parentColumns = ["cake_id"],
            childColumns = ["cake_id"],
            onDelete = ForeignKey.CASCADE
        ),
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
    indices = [Index("cake_id"), Index("shape_id"), Index("size_id")]
)
data class TierEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tier_id") val id: Int = 0,
    @ColumnInfo(name = "cake_id") val cakeId: Int,
    @ColumnInfo(name = "level") val level: Int,
    @ColumnInfo(name = "shape_id") val shapeId: Int,
    @ColumnInfo(name = "size_id") val sizeId: Int,
    @ColumnInfo(name = "color_hex") val colorHex: String,
    @ColumnInfo(name = "price") val price: Double
)