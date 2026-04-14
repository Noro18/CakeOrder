package com.example.ordermanagementcake.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ordermanagementcake.data.local.dao.ClientDao
import com.example.ordermanagementcake.data.local.dao.OrderDao
import com.example.ordermanagementcake.data.local.dao.OrderItemDao
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                )
                    .addCallback(PrepopulateCallback())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
    private class PrepopulateCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    seedDatabase(database)  // ← calls your seed function
                }
            }
        }
    }
}


