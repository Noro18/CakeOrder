package com.example.ordermanagementcake.data.local

import android.content.Context
import androidx.databinding.adapters.Converters
import com.example.ordermanagementcake.data.local.Converter
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ordermanagementcake.data.local.dao.CakeDao
import com.example.ordermanagementcake.data.local.dao.ClientDao
import com.example.ordermanagementcake.data.local.dao.OrderDao
import com.example.ordermanagementcake.data.local.dao.PriceTableDao
import com.example.ordermanagementcake.data.local.dao.ShapeDao
import com.example.ordermanagementcake.data.local.dao.SizeDao
import com.example.ordermanagementcake.data.local.dao.TierDao
import com.example.ordermanagementcake.data.local.entities.CakeEntity
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.PriceTableEntity
import com.example.ordermanagementcake.data.local.entities.ShapeEntity
import com.example.ordermanagementcake.data.local.entities.SizeEntity
import com.example.ordermanagementcake.data.local.entities.TierEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 1. Register all 7 entities so Room creates all 7 tables
// 2. @TypeConverters tells Room how to handle OrderStatus enum
// 3. version = 2 because the schema changed from the old 3-table design
@TypeConverters(Converter::class)
@Database(
    entities = [
        ClientEntity::class,
        OrderEntity::class,
        CakeEntity::class,
        TierEntity::class,
        ShapeEntity::class,
        SizeEntity::class,
        PriceTableEntity::class
    ],
    version = 4
)
abstract class OrderDatabase : RoomDatabase() {

    // Room generates the actual implementation of these at compile time
    // Every DAO you want to use must be declared here
    abstract fun clientDao(): ClientDao
    abstract fun orderDao(): OrderDao
    abstract fun cakeDao(): CakeDao
    abstract fun tierDao(): TierDao
    abstract fun shapeDao(): ShapeDao
    abstract fun sizeDao(): SizeDao
    abstract fun priceTableDao(): PriceTableDao

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
                    // 4. Since we're in development, just wipe and recreate
                    // when Room detects the version bumped from 1 to 2
                    // Remove this when the app goes to production!
                    .fallbackToDestructiveMigration() // t
                    .addCallback(PrepopulateCallback())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    // 5. This runs once when the DB is first created on the device
    // It calls seedDatabase() to pre-populate with sample data
    private class PrepopulateCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    seedDatabase(database)
                }
            }
        }
    }
}
