package com.example.ordermanagementcake

import com.example.ordermanagementcake.data.local.entities.OrderStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class OrderStatusTest {

    @Test
    fun `test nextStatus transitions`() {
        assertEquals(OrderStatus.IN_PROGRESS, OrderStatus.PENDING.nextStatus())
        assertEquals(OrderStatus.READY, OrderStatus.IN_PROGRESS.nextStatus())
        assertEquals(OrderStatus.COMPLETED, OrderStatus.READY.nextStatus())
    }

    @Test
    fun `test terminal statuses have no nextStatus`() {
        assertNull(OrderStatus.COMPLETED.nextStatus())
        assertNull(OrderStatus.CANCELLED.nextStatus())
    }
}
