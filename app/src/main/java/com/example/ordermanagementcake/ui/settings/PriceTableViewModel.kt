package com.example.ordermanagementcake.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ordermanagementcake.data.local.entities.PriceTableEntity
import com.example.ordermanagementcake.data.local.entities.ShapeEntity
import com.example.ordermanagementcake.data.local.entities.SizeEntity
import com.example.ordermanagementcake.data.repository.PriceTableRepository
import com.example.ordermanagementcake.data.repository.ShapeRepository
import com.example.ordermanagementcake.data.repository.SizeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ShapePriceEntry(
    val priceId: Int,
    val sizeInches: Double,
    val price: Double
)

data class ShapeGroup(
    val shapeId: Int,
    val shapeName: String,
    val prices: List<ShapePriceEntry>
)

data class PriceTableUiState(
    val shapeGroups: List<ShapeGroup> = emptyList(),
    val isLoading: Boolean = true
)

class PriceTableViewModel(
    private val priceTableRepository: PriceTableRepository,
    private val shapeRepository: ShapeRepository,
    private val sizeRepository: SizeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PriceTableUiState())
    val uiState: StateFlow<PriceTableUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        combine(
            priceTableRepository.getAllPrices(),
            shapeRepository.getAllShapes(),
            sizeRepository.getAllSizes()
        ) { prices, shapes, sizes ->
            val shapeMap = shapes.associateBy { it.id }
            val sizeMap = sizes.associateBy { it.id }

            val groups = prices.groupBy { it.shapeId }
                .mapNotNull { (shapeId, priceEntities) ->
                    val shape = shapeMap[shapeId] ?: return@mapNotNull null
                    ShapeGroup(
                        shapeId = shapeId,
                        shapeName = shape.shapeName,
                        prices = priceEntities.map {
                            ShapePriceEntry(
                                priceId = it.id,
                                sizeInches = sizeMap[it.sizeId]?.inches ?: 0.0,
                                price = it.price
                            )
                        }.sortedBy { it.sizeInches }
                    )
                }.sortedBy { it.shapeName }
            
            groups
        }.onEach { groups ->
            _uiState.update { it.copy(shapeGroups = groups, isLoading = false) }
        }.launchIn(viewModelScope)
    }

    fun addPrice(shapeId: Int, sizeInches: Double, price: Double) {
        viewModelScope.launch {
            // 1. Find or create size
            val sizes = sizeRepository.getAllSizes().first()
            var size = sizes.find { it.inches == sizeInches }
            
            val sizeId = if (size == null) {
                sizeRepository.insertSize(SizeEntity(inches = sizeInches)).toInt()
            } else {
                size.id
            }

            // 2. Insert into price table (or update if already exists)
            val existingPrice = priceTableRepository.getPrice(shapeId, sizeId)
            if (existingPrice == null) {
                priceTableRepository.insertPrice(
                    PriceTableEntity(
                        shapeId = shapeId,
                        sizeId = sizeId,
                        price = price
                    )
                )
            } else {
                priceTableRepository.updatePrice(
                    existingPrice.copy(price = price)
                )
            }
        }
    }

    fun updatePrice(priceId: Int, shapeId: Int, sizeInches: Double, price: Double) {
        viewModelScope.launch {
            // 1. Find or create size
            val sizes = sizeRepository.getAllSizes().first()
            val size = sizes.find { it.inches == sizeInches }
            
            val sizeId = if (size == null) {
                sizeRepository.insertSize(SizeEntity(inches = sizeInches)).toInt()
            } else {
                size.id
            }

            // 2. Update specific entry by ID
            priceTableRepository.updatePrice(
                PriceTableEntity(
                    id = priceId,
                    shapeId = shapeId,
                    sizeId = sizeId,
                    price = price
                )
            )
        }
    }

    fun deletePrice(priceId: Int) {
        viewModelScope.launch {
            // We can delete by passing an entity with just the ID
            priceTableRepository.deletePrice(
                PriceTableEntity(
                    id = priceId,
                    shapeId = 0,
                    sizeId = 0,
                    price = 0.0
                )
            )
        }
    }
}

class PriceTableViewModelFactory(
    private val priceTableRepository: PriceTableRepository,
    private val shapeRepository: ShapeRepository,
    private val sizeRepository: SizeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PriceTableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PriceTableViewModel(priceTableRepository, shapeRepository, sizeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
