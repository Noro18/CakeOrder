package com.example.ordermanagementcake.ui.orders

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermanagementcake.data.draft.CakeDraft
import com.example.ordermanagementcake.data.draft.OrderDraft
import com.example.ordermanagementcake.data.local.entities.CakeEntity
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.data.local.entities.OrderEntity
import com.example.ordermanagementcake.data.local.entities.OrderStatus
import com.example.ordermanagementcake.data.local.entities.ShapeEntity
import com.example.ordermanagementcake.data.local.entities.SizeEntity
import com.example.ordermanagementcake.data.local.entities.TierEntity
import com.example.ordermanagementcake.data.repository.CakeRepository
import com.example.ordermanagementcake.data.repository.ClientRepository
import com.example.ordermanagementcake.data.repository.OrderRepository
import com.example.ordermanagementcake.data.repository.ShapeRepository
import com.example.ordermanagementcake.data.repository.SizeRepository
import com.example.ordermanagementcake.data.repository.TierRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewOrderViewModel(
    private val orderRepository: OrderRepository,
    private val cakeRepository: CakeRepository,
    private val tierRepository: TierRepository,
    private val shapeRepository: ShapeRepository,
    private val sizeRepository: SizeRepository,
    private val clientRepository: ClientRepository
) : ViewModel() {

    companion object {
        private const val TAG = "NewOrderViewModel"
    }

    var orderDraft by mutableStateOf(OrderDraft())
        private set

    // Client search logic
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val clientSuggestions: StateFlow<List<ClientEntity>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.length < 2) MutableStateFlow(emptyList())
            else clientRepository.searchClientsByName(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onClientSearchQueryChange(query: String) {
        _searchQuery.value = query
        updateOrderDraft { it.copy(clientName = query) }

        // Auto-select if exact match found
        viewModelScope.launch {
            val exactMatch = clientRepository.getClientByName(query)
            if (exactMatch != null) {
                Log.d(TAG, "Exact client match found: ${exactMatch.name} (ID: ${exactMatch.id})")
                selectClient(exactMatch)
            } else {
                // If user changed the name and it's no longer a match, clear the ID
                if (orderDraft.clientId != null) {
                    updateOrderDraft { it.copy(clientId = null) }
                }
            }
        }
    }

    fun selectClient(client: ClientEntity) {
        _searchQuery.value = client.name
        updateOrderDraft { it.copy(clientId = client.id, clientName = client.name) }
    }

    fun updateOrderDraft(update: (OrderDraft) -> OrderDraft) {
        orderDraft = update(orderDraft)
    }

    fun addCakeToDraft(cake: CakeDraft) {
        orderDraft = orderDraft.copy(cakes = orderDraft.cakes + cake)
        calculateTotalPrice()
    }
    
    fun removeCakeFromDraft(index: Int) {
        val newCakes = orderDraft.cakes.toMutableList()
        if (index in newCakes.indices) {
            newCakes.removeAt(index)
            orderDraft = orderDraft.copy(cakes = newCakes)
            calculateTotalPrice()
        }
    }

    private fun calculateTotalPrice() {
        val total = orderDraft.cakes.sumOf { cake ->
            cake.tiers.sumOf { it.price }
        }
        orderDraft = orderDraft.copy(totalPrice = total)
    }

    fun resetDraft() {
        orderDraft = OrderDraft()
        _searchQuery.value = ""
    }

    fun saveOrder(onSuccess: () -> Unit = {}) {
        Log.d(TAG, "Starting saveOrder process...")
        
        viewModelScope.launch {
            try {
                // 1. Resolve a valid Client ID
                var finalClientId = orderDraft.clientId
                
                if (finalClientId == null || finalClientId == 0) {
                    Log.d(TAG, "No Client ID in draft. Searching for existing clients...")
                    val allClients = clientRepository.getAllClients().first()
                    if (allClients.isNotEmpty()) {
                        finalClientId = allClients.first().id
                        Log.d(TAG, "Found existing client. Using ID: $finalClientId")
                    } else {
                        Log.e(TAG, "CRITICAL: No clients exist in database. You must add a client first!")
                        return@launch
                    }
                }

                Log.d(TAG, "Inserting Order for client ID: $finalClientId")
                // A. Save Order (Get ID)
                val orderId = orderRepository.insertOrder(
                    OrderEntity(
                        customerId = finalClientId!!,
                        orderDate = orderDraft.orderDate.ifEmpty { "2026-06-02" },
                        deliveryDate = orderDraft.deliveryDate,
                        totalPrice = orderDraft.totalPrice,
                        orderNotes = orderDraft.orderNotes,
                        status = OrderStatus.PENDING
                    )
                ).toInt()
                Log.d(TAG, "Order inserted successfully. ID: $orderId")

                // B. Loop Cakes
                orderDraft.cakes.forEachIndexed { index, cakeDraft ->
                    Log.d(TAG, "Inserting Cake #$index: ${cakeDraft.cakeTitle}")
                    val cakeId = cakeRepository.insertCake(
                        CakeEntity(
                            orderId = orderId,
                            cakeTitle = cakeDraft.cakeTitle,
                            cakeNotes = cakeDraft.cakeNotes,
                            imageUri = cakeDraft.imageUri
                        )
                    ).toInt()
                    Log.d(TAG, "Cake inserted successfully. ID: $cakeId")

                    // C. Loop Tiers
                    cakeDraft.tiers.forEach { tierDraft ->
                        Log.d(TAG, "Inserting Tier level ${tierDraft.level} for Cake ID: $cakeId")
                        val shapeId = getShapeId(tierDraft.shape)
                        val sizeId = getSizeId(tierDraft.size)

                        tierRepository.insertTier(
                            TierEntity(
                                cakeId = cakeId,
                                level = tierDraft.level,
                                shapeId = shapeId,
                                sizeId = sizeId,
                                colorHex = colorToHexString(tierDraft.color),
                                price = tierDraft.price
                            )
                        )
                    }
                }
                
                Log.d(TAG, "Full recursive save complete. Resetting draft and calling onSuccess.")
                resetDraft()
                onSuccess()
            } catch (e: Exception) {
                Log.e(TAG, "Error saving order: ${e.message}", e)
            }
        }
    }

    private suspend fun getShapeId(shapeName: String): Int {
        val shapes = shapeRepository.getAllShapes().first()
        val existing = shapes.find { it.shapeName.equals(shapeName, ignoreCase = true) }
        return existing?.id ?: shapeRepository.insertShape(ShapeEntity(shapeName = shapeName)).toInt()
    }

    private suspend fun getSizeId(sizeLabel: String): Int {
        val inches = sizeLabel.replace("inch", "").toDoubleOrNull() ?: 0.0
        val sizes = sizeRepository.getAllSizes().first()
        val existing = sizes.find { it.inches == inches }
        return existing?.id ?: sizeRepository.insertSize(SizeEntity(inches = inches)).toInt()
    }

    private fun colorToHexString(color: Color): String {
        return String.format("#%06X", (0xFFFFFF and color.toArgb()))
    }
}
