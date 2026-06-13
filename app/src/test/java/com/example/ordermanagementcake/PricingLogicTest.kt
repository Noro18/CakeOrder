    package com.example.ordermanagementcake

import com.example.ordermanagementcake.data.local.entities.ShapeEntity
import com.example.ordermanagementcake.data.local.entities.SizeEntity
import com.example.ordermanagementcake.data.local.entities.PriceTableEntity
import com.example.ordermanagementcake.data.repository.*
import com.example.ordermanagementcake.ui.orders.NewOrderViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class PricingLogicTest {

    @Test
    fun `test getPriceFor returns correct price`() = runBlocking {
        // Mock repositories
        val mockOrderRepo = mock(OrderRepository::class.java)
        val mockCakeRepo = mock(CakeRepository::class.java)
        val mockTierRepo = mock(TierRepository::class.java)
        val mockShapeRepo = mock(ShapeRepository::class.java)
        val mockSizeRepo = mock(SizeRepository::class.java)
        val mockClientRepo = mock(ClientRepository::class.java)
        val mockPriceTableRepo = mock(PriceTableRepository::class.java)

        // Setup mock data
        val circle = ShapeEntity(id = 1, shapeName = "Circle")
        val size8 = SizeEntity(id = 1, inches = 8.0)
        val priceEntry = PriceTableEntity(id = 1, shapeId = 1, sizeId = 1, price = 45.0)

        // Mock repository responses
        `when`(mockShapeRepo.getAllShapes()).thenReturn(flowOf(listOf(circle)))
        `when`(mockSizeRepo.getAllSizes()).thenReturn(flowOf(listOf(size8)))
        `when`(mockPriceTableRepo.getPrice(1, 1)).thenReturn(priceEntry)

        // Initialize ViewModel
        val viewModel = NewOrderViewModel(
            mockOrderRepo,
            mockCakeRepo,
            mockTierRepo,
            mockShapeRepo,
            mockSizeRepo,
            mockClientRepo,
            mockPriceTableRepo
        )

        // Wait for StateFlows to collect data
        // Note: With StateFlow, this might need a bit of time or a specific test dispatcher
        // Given the simplified nature, let's see if it works.
        
        // Execute and verify
        val price = viewModel.getPriceFor("Circle", "8inch")
        assertEquals(45.0, price, 0.0)
    }
}
