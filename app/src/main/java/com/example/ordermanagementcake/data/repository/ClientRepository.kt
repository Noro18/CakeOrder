package com.example.ordermanagementcake.data.repository

import com.example.ordermanagementcake.data.local.dao.ClientDao
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.relations.ClientWithOrders
import kotlinx.coroutines.flow.Flow

class ClientRepository(private val clientDao: ClientDao) {

    fun searchClientsByName(query: String): Flow<List<ClientEntity>> =
        clientDao.searchClientsByName(query)

    suspend fun getClientByName(name: String): ClientEntity? =
        clientDao.getClientByName(name)

    fun getAllClients(): Flow<List<ClientEntity>> =
        clientDao.getAllClients()

    fun getClientById(id: Int) =
        clientDao.getClientById(id)

    fun getClientWithOrders(id: Int): Flow<ClientWithOrders?> =
        clientDao.getClientWithOrders(id)

    suspend fun findByPhone(phone: String): ClientEntity? =
        clientDao.findByPhone(phone)

    suspend fun insertClient(client: ClientEntity): Long =
        clientDao.insertClient(client)

    suspend fun updateClient(client: ClientEntity) =
        clientDao.updateClient(client)

    suspend fun deleteClient(client: ClientEntity) =
        clientDao.deleteClient(client)

    fun countClients(): Flow<Int> =
        clientDao.countClients()
}