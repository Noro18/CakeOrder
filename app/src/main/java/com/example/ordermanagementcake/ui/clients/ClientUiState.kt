package com.example.ordermanagementcake.ui.clients

import com.example.ordermanagementcake.data.local.entities.ClientEntity

data class ClientUiState(
    val clients: List<ClientEntity> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)