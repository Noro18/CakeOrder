package com.example.ordermanagementcake.ui.clients

import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.relations.ClientWithOrders

data class ClientUiState(
    val clients: List<ClientEntity> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val selectedClient: ClientWithOrders? = null  // uza ClientWithOrder laos ClientEntity tamba iha client detail ne iha Order History hotu
)