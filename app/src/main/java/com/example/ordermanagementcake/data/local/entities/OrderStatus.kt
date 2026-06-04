package com.example.ordermanagementcake.data.local.entities


// Clas ida ne'e ba aty rai orer status sira ne'eb ita deifine ona iha planemanetu

enum class OrderStatus {
    PENDING,
    IN_PROGRESS,
    READY,
    COMPLETED,
    CANCELLED;

    /** Returns the next logical status, or null if the order is already terminal. */
    fun nextStatus(): OrderStatus? = when (this) {
        PENDING     -> IN_PROGRESS
        IN_PROGRESS -> READY
        READY       -> COMPLETED
        else        -> null   // COMPLETED / CANCELLED — nothing to do
    }
}