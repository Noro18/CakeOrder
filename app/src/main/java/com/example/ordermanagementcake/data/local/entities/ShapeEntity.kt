package com.example.ordermanagementcake.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shapes")
data class ShapeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shape_id") val id: Int = 0,
    @ColumnInfo(name = "shape_name") val shapeName: String
)
