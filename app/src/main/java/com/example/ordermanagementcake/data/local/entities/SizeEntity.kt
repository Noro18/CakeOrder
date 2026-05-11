package com.example.ordermanagementcake.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "sizes"
)
data class SizeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "size_id") val id: Int = 0,
    @ColumnInfo(name = "inches") val inches: Double
)
