package com.example.ordermanagementcake.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ordermanagementcake.data.local.dao.ClientDao
import com.example.ordermanagementcake.data.local.dao.OrderDao
import com.example.ordermanagementcake.data.local.dao.OrderItemDao
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderItemEntity

// degfine nia database Room iah ne'e
@Database (
    entities = [ClientEntity::class, OrderEntity::class, OrderItemEntity::class], // register tabelar sira iha ne'e
    version = 1 // versuan actuall husi database, se kark ita muda schema database mak
)

abstract class OrderDatabase: RoomDatabase() {

    // Implementa DAO sira atu Repository no ViewModle bele accesss DAO
    abstract fun clientDao(): ClientDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao


    // Code iha kraik ne'e atu make sure database ne kria dala ida deit (Singleton)
    companion object {
        @Volatile
        private var INSTANCE: OrderDatabase? = null

        fun getInstance(context: Context): OrderDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    OrderDatabase::class.java,
                    "order_management_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
