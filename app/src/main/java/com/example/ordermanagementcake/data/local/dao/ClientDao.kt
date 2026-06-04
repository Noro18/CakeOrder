package com.example.ordermanagementcake.data.local.dao

import androidx.room.*
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.relations.ClientWithOrders
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Query("SELECT * FROM clients")
    fun getAllClients(): Flow<List<ClientEntity>>

    @Query("SELECT * FROM clients WHERE client_id = :id")
    fun getClientById(id: Int): Flow<ClientEntity?>

    @Query("SELECT * FROM clients WHERE phone = :phone")
    suspend fun findByPhone(phone: String): ClientEntity?

    @Query("SELECT * FROM clients WHERE name LIKE '%' || :query || '%'")
    fun searchClientsByName(query: String): Flow<List<ClientEntity>>

    @Query("SELECT * FROM clients WHERE name = :name LIMIT 1")
    suspend fun getClientByName(name: String): ClientEntity?

    @Transaction
    @Query("SELECT * FROM clients WHERE client_id = :id")
    fun getClientWithOrders(id: Int): Flow<ClientWithOrders?>

    @Insert
    suspend fun insertClient(client: ClientEntity): Long

    @Update
    suspend fun updateClient(client: ClientEntity)

    @Delete
    suspend fun deleteClient(client: ClientEntity)
}