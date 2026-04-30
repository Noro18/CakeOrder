package com.example.ordermanagementcake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class DrawerItem(
    var label: String,
    val icon: ImageVector,
    val onCLick: () -> Unit
)

@Composable
fun AppDrawer(
    onClose: () -> Unit
) {
    val items = listOf(
            DrawerItem("Dashboard",  Icons.Default.Dashboard)   { onClose() },
        DrawerItem("Orders",     Icons.Default.AddShoppingCart) { onClose() },
        DrawerItem("Clients",    Icons.Default.People)      { onClose() },
        DrawerItem("Schedules",  Icons.Default.CalendarMonth) { onClose() },
    )

    val bottomItems = listOf(
        DrawerItem("Settings",   Icons.Default.Settings)    { onClose() },
        DrawerItem("About",      Icons.Default.Info)        { onClose() },
    )

    ModalDrawerSheet() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC23C12))
                .padding(24.dp)
        ) {
            Column() {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Store,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

