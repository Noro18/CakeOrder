package com.example.ordermanagementcake

import androidx.compose.ui.graphics.Color
import com.example.ordermanagementcake.data.draft.CakeDraft
import com.example.ordermanagementcake.data.draft.OrderDraft
import com.example.ordermanagementcake.data.draft.TierDraft
import org.junit.Assert.assertEquals
import org.junit.Test

class OrderDraftTest {

    @Test
    fun `test calculateTotal with multiple cakes and tiers`() {
        val tier1 = TierDraft(level = 1, shape = "Round", size = "20inch", color = Color.White, price = 100.0)
        val tier2 = TierDraft(level = 2, shape = "Round", size = "15inch", color = Color.Magenta, price = 50.0)
        
        val cake1 = CakeDraft(cakeTitle = "Wedding Cake", tiers = listOf(tier1, tier2))
        
        val tier3 = TierDraft(level = 1, shape = "Heart", size = "10inch", color = Color.Red, price = 30.0)
        val cake2 = CakeDraft(cakeTitle = "Birthday Cake", tiers = listOf(tier3))
        
        val draft = OrderDraft(cakes = listOf(cake1, cake2))
        
        assertEquals(180.0, draft.calculateTotal(), 0.001)
    }

    @Test
    fun `test calculateTotal with empty cakes`() {
        val draft = OrderDraft(cakes = emptyList())
        assertEquals(0.0, draft.calculateTotal(), 0.001)
    }

    @Test
    fun `test calculateTotal with cake having no tiers`() {
        val cake = CakeDraft(cakeTitle = "Empty Cake", tiers = emptyList())
        val draft = OrderDraft(cakes = listOf(cake))
        assertEquals(0.0, draft.calculateTotal(), 0.001)
    }
}
