package com.example.ordermanagementcake.data.draft

import androidx.compose.ui.graphics.Color

data class TierDraft(
    val level: Int,
    val shape: String,
    val size: String,
    val color: Color,
    val price: Double
)

data class CakeDraft(
    val cakeTitle: String,
    val cakeNotes: String = "",
    val imageUri: String? = null,
    val tiers: List<TierDraft> = emptyList()
)

data class OrderDraft(
    val clientId: Int? = null,
    val clientName: String? = null,
    val orderDate: String = "",
    val deliveryDate: String = "",
    val orderNotes: String = "",
    val totalPrice: Double = 0.0,
    val cakes: List<CakeDraft> = emptyList()
) {
    fun calculateTotal(): Double {
        return cakes.sumOf { cake ->
            cake.tiers.sumOf { it.price }
        }
    }
}
