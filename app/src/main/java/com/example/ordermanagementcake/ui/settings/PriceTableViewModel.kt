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

data class PriceEntry(
    val priceId: Int,
    val shapeName: String,
    val sizeInches: Double,
    val price: Double
)

data class PriceTableUiState(
    val prices: List<PriceEntry> = emptyList(),
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

            prices.map { priceEntity ->
                PriceEntry(
                    priceId = priceEntity.id,
                    shapeName = shapeMap[priceEntity.shapeId]?.shapeName ?: "Unknown",
                    sizeInches = sizeMap[priceEntity.sizeId]?.inches ?: 0.0,
                    price = priceEntity.price
                )
            }
        }.onEach { priceEntries ->
            _uiState.update { it.copy(prices = priceEntries, isLoading = false) }
        }.launchIn(viewModelScope)
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
