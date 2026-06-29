package com.example.ordermanagementcake.ui.dashboard

data class DashboardUiState(
    val todayOrderCount: Int = 0,
    val pendingCount: Int = 0,
    val readyCount: Int = 0,
    val completedCount: Int = 0,
    val totalClients: Int = 0,
    val totalRevenue: Double = 0.0,
    val timelineItems: List<TimelineItem> = emptyList(),
    val pickupItems: List<PickupItem> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

data class TimelineItem(
    val time: String,
    val client: String,
    val cake: String,
    val status: String
)

data class PickupItem(
    val client: String,
    val cake: String,
    val daysLeft: String,
    val isUrgent: Boolean
)
