package com.example.ordermanagementcake.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

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
            label = {
                Text(
                    text = "DASHBOARD",
                    fontSize = 10.sp
                )
                    },
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
            label = {
                Text(
                    text = "ORDERS",
                    fontSize = 10.sp
                )
                    },
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
            label = {
                Text(
                    text = "CLIENTS",
                    fontSize = 10.sp
                )
                    },
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
            label = {
                Text(
                    text = "SCHEDULES",
                    fontSize = 10.sp
                )
                    },
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