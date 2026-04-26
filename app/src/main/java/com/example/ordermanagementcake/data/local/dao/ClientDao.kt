package com.example.ordermanagementcake.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.relations.ClientWithOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    // Select all clients
    @Query("SELECT * FROM clients")
    fun getAllCLients(): Flow<List<ClientEntity>> // Flow ne'e return stemas of data non stop

     @Query("SELECT * FROM clients WHERE phoneNumber = :phoneNumber")
     suspend fun findByWhatsapp(phoneNumber: String): ClientEntity?


     // Sei confusaun ???
     @Transaction // make usre to use "atomicity"
     @Query("SELECT * FROM clients WHERE id = :id")
     fun getClientWithOrder(id: Int): Flow<ClientWithOrder> // Return type laos ClientEntity maiube ClientWithOrders

    @Insert // no need for Query, Room knows how to generate hte INSERT to
    suspend fun insertClient(client: ClientEntity): Long

    @Update
    suspend fun updateClient(client: ClientEntity)

    @Delete
    suspend fun deleteClient(client: ClientEntity)


     

}