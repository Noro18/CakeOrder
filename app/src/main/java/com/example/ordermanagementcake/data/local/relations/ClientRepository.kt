package com.example.ordermanagementcake.data.local.relations

import com.example.ordermanagementcake.data.local.dao.ClientDao
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import kotlinx.coroutines.flow.Flow

class ClientRepository(private val clientDao: ClientDao) {

    fun getAllClients(): Flow<List<ClientEntity>> =
        clientDao.getAllCLients()

    fun getClientWithOrders(id: Int): Flow<ClientWithOrder> =
        clientDao.getClientWithOrder(id)

    suspend fun findByWhatsapp(phoneNumber: String): ClientEntity? =
        clientDao.findByWhatsapp(phoneNumber)

    suspend fun insertClient(client: ClientEntity): Long =
        clientDao.insertClient(client)

    suspend fun updateClient(client: ClientEntity) =
        clientDao.updateClient(client)

    suspend fun deleteClient(client: ClientEntity) =
        clientDao.deleteClient(client)
}