package com.example.ordermanagementcake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DrawerItem(
    var label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
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
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "The Artisanal Bakery",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Order Management",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp
                )
            }
        }
        Spacer(Modifier.height(8.dp))

        // ── Navigation items ──────────────────────────────────
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) },
                selected = false,
                onClick = item.onClick,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))

        // ── Bottom items (Settings, About) ────────────────────
        bottomItems.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) },
                selected = false,
                onClick = item.onClick,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}

