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
import com.example.ordermanagementcake.data.draft.TierDraft
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
import com.example.ordermanagementcake.data.repository.PriceTableRepository
import com.example.ordermanagementcake.data.repository.ShapeRepository
import com.example.ordermanagementcake.data.repository.SizeRepository
import com.example.ordermanagementcake.data.repository.TierRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import com.example.ordermanagementcake.notifications.AlarmScheduler
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewOrderViewModel(
    private val orderRepository: OrderRepository,
    private val cakeRepository: CakeRepository,
    private val tierRepository: TierRepository,
    private val shapeRepository: ShapeRepository,
    private val sizeRepository: SizeRepository,
    private val clientRepository: ClientRepository,
    private val priceTableRepository: PriceTableRepository,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

    companion object {
        private const val TAG = "NewOrderViewModel"
    }

    var orderDraft by mutableStateOf(OrderDraft())
        private set

    // Auto-pricing data
    val shapes: StateFlow<List<ShapeEntity>> = shapeRepository.getAllShapes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sizes: StateFlow<List<SizeEntity>> = sizeRepository.getAllSizes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    suspend fun getPriceFor(shapeName: String, sizeLabel: String): Double? {
        Log.d(TAG, "getPriceFor: shape=$shapeName, size=$sizeLabel")
        val shapeId = shapes.value.find { it.shapeName.equals(shapeName, ignoreCase = true) }?.id
        val inches = sizeLabel.replace("inch", "").toDoubleOrNull() ?: 0.0
        val sizeId = sizes.value.find { it.inches == inches }?.id
        Log.d(TAG, "getPriceFor: shapeId=$shapeId, sizeId=$sizeId, inches=$inches")

        if (shapeId != null && sizeId != null) {
            val price = priceTableRepository.getPrice(shapeId, sizeId)?.price
            Log.d(TAG, "getPriceFor: found price=$price")
            return price
        }
        Log.d(TAG, "getPriceFor: not found")
        return null
    }

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
        orderDraft = orderDraft.copy(totalPrice = orderDraft.calculateTotal())
    }

    fun initForEdit(orderId: Int) {
        viewModelScope.launch {
            try {
                val orderDetail = orderRepository.getOrderFullDetail(orderId).first() ?: return@launch
                val allShapes = shapeRepository.getAllShapes().first()
                val allSizes = sizeRepository.getAllSizes().first()

                val cakeDrafts = orderDetail.cakes.map { cakeWithTiers ->
                    CakeDraft(
                        cakeTitle = cakeWithTiers.cake.cakeTitle,
                        cakeNotes = cakeWithTiers.cake.cakeNotes,
                        imageUri = cakeWithTiers.cake.imageUri,
                        bakingDate = cakeWithTiers.cake.bakingDate,
                        tiers = cakeWithTiers.tiers.map { tier ->
                            TierDraft(
                                level = tier.level,
                                shape = allShapes.find { it.id == tier.shapeId }?.shapeName ?: "Circle",
                                size = "${allSizes.find { it.id == tier.sizeId }?.inches ?: tier.sizeId}\"",
                                color = try { Color(android.graphics.Color.parseColor(tier.colorHex)) } catch (e: Exception) { Color.Gray },
                                price = tier.price
                            )
                        }
                    )
                }

                orderDraft = OrderDraft(
                    orderId = orderDetail.order.id,
                    clientId = orderDetail.client.id,
                    clientName = orderDetail.client.name,
                    deliveryDate = orderDetail.order.deliveryDate,
                    orderNotes = orderDetail.order.orderNotes,
                    totalPrice = orderDetail.order.totalPrice,
                    cakes = cakeDrafts
                )
                _searchQuery.value = orderDetail.client.name
            } catch (e: Exception) {
                Log.e(TAG, "Error loading order for edit: ${e.message}", e)
            }
        }
    }

    fun resetDraft() {
        orderDraft = OrderDraft()
        _searchQuery.value = ""
    }

    fun saveOrder(onSuccess: () -> Unit = {}) {
        Log.d(TAG, "Starting saveOrder process...")

        viewModelScope.launch {
            try {
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

                val isEditing = orderDraft.orderId != null
                val orderId: Int

                if (isEditing) {
                    // === EDIT MODE ===
                    orderId = orderDraft.orderId!!
                    val existing = orderRepository.getOrderFullDetail(orderId).first()
                    if (existing == null) {
                        Log.e(TAG, "Order to edit not found: $orderId")
                        return@launch
                    }

                    Log.d(TAG, "Updating Order ID: $orderId")
                    orderRepository.updateOrder(
                        existing.order.copy(
                            customerId = finalClientId!!,
                            deliveryDate = orderDraft.deliveryDate,
                            totalPrice = orderDraft.totalPrice,
                            orderNotes = orderDraft.orderNotes
                        )
                    )

                    cakeRepository.deleteCakesForOrder(orderId)
                } else {
                    // === CREATE MODE ===
                    Log.d(TAG, "Inserting Order for client ID: $finalClientId")
                    orderId = orderRepository.insertOrder(
                        OrderEntity(
                            customerId = finalClientId!!,
                            orderDate = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date()),
                            deliveryDate = orderDraft.deliveryDate,
                            totalPrice = orderDraft.totalPrice,
                            orderNotes = orderDraft.orderNotes,
                            status = OrderStatus.PENDING
                        )
                    ).toInt()
                    Log.d(TAG, "Order inserted successfully. ID: $orderId")
                }

                // Insert cakes and tiers (same for both modes)
                orderDraft.cakes.forEachIndexed { index, cakeDraft ->
                    Log.d(TAG, "Inserting Cake #$index: ${cakeDraft.cakeTitle}")
                    val cakeId = cakeRepository.insertCake(
                        CakeEntity(
                            orderId = orderId,
                            cakeTitle = cakeDraft.cakeTitle,
                            cakeNotes = cakeDraft.cakeNotes,
                            imageUri = cakeDraft.imageUri,
                            bakingDate = cakeDraft.bakingDate
                        )
                    ).toInt()
                    Log.d(TAG, "Cake inserted successfully. ID: $cakeId")

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

                Log.d(TAG, "Save complete. Resetting draft and calling onSuccess.")
                scheduleOrderAlarms(orderId.toString(), orderDraft.deliveryDate)
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

    private fun scheduleOrderAlarms(orderId: String, deliveryDateStr: String) {
        if (deliveryDateStr.isBlank()) return
        
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            val date = format.parse(deliveryDateStr) ?: return
            
            val timeInMillis = date.time
            val thirtyMinsInMillis = 30 * 60 * 1000L
            
            val messageExact = "Order #$orderId is scheduled for delivery/pickup NOW!"
            val message30 = "Order #$orderId is scheduled for delivery/pickup in 30 minutes."
            
            alarmScheduler.scheduleAlarm(orderId, timeInMillis, messageExact, true)
            alarmScheduler.scheduleAlarm(orderId, timeInMillis - thirtyMinsInMillis, message30, false)
            
            Log.d(TAG, "Alarms scheduled for Order ID: $orderId at $deliveryDateStr")
        } catch (e: Exception) {
            Log.e(TAG, "Error scheduling alarms: ${e.message}", e)
        }
    }

    /**
     * TESTING UTILITY:
     * This function is strictly for debugging and UI testing purposes. 
     * It schedules a dummy notification to fire exactly 5 seconds from when it is called.
     * 
     * How to test: 
     * Temporarily attach this function to any onClick event in the UI (e.g., a "Test Alarm" button).
     * Click the button, minimize the app, and wait 5 seconds to verify that the NotificationReceiver
     * and AlarmManager are functioning properly and bypassing battery optimizations.
     */
    fun testNotification() {
        // Schedules an alarm to fire 5 seconds from now for immediate UI testing
        val timeInMillis = System.currentTimeMillis() + 5000L
        val testOrderId = "TEST_999"
        val message = "This is a 5-second test notification!"
        
        alarmScheduler.scheduleAlarm(testOrderId, timeInMillis, message, true)
        Log.d(TAG, "Test notification scheduled for 5 seconds from now.")
    }
}
