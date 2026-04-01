package com.example.ordermanagementcake.ui.theme


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
            label = { Text("DASHBOARD") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFC23C12),
                selectedTextColor = Color(0xFFC23C12),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFF39D82)
            )
        )
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) },
            icon = { Icon(Icons.Default.AddShoppingCart, contentDescription = "Orders") },
            label = { Text("ORDERS") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFC23C12),
                selectedTextColor = Color(0xFFC23C12),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFF39D82)
            )
        )
        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = { onItemSelected(2) },
            icon = { Icon(Icons.Default.People, contentDescription = "Clients") },
            label = { Text("CLIENTS") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFC23C12),
                selectedTextColor = Color(0xFFC23C12),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFF39D82)
            )
        )
        NavigationBarItem(
            selected = selectedItem == 3,
            onClick = { onItemSelected(3) },
            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Schedule") },
            label = { Text("SCHEDULES") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFC23C12),
                selectedTextColor = Color(0xFFC23C12),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFF39D82)
            )
        )
    }
}