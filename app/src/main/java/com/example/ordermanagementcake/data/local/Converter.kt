package com.example.ordermanagementcake.data.local

import androidx.room.TypeConverter
import com.example.ordermanagementcake.data.local.entities.OrderStatus


// ne'e clas ida par atu convert value enum object ba string
class Converter {
    @TypeConverter
    fun fromOrderStatus(status: OrderStatus): String {
        return status.name
    }

    @TypeConverter
    fun toOrderStatus(value: String): OrderStatus {
        return OrderStatus.valueOf(value) // reads the string back into the enum
    }
}