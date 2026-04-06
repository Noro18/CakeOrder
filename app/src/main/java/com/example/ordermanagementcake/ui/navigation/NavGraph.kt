package com.example.ordermanagementcake.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ordermanagementcake.ui.clients.ClientsListScreen
import com.example.ordermanagementcake.ui.orders.OrderListScreen

object Routes {
    const val ORDERS = "orders"
    const val CLIENTS = "clients"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.ORDERS) {
        composable(Routes.ORDERS) {
            OrderListScreen(navController)
        }
        composable(Routes.CLIENTS) {
            ClientsListScreen(navController)
        }
    }
 }
