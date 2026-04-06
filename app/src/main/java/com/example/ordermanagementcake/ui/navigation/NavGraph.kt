package com.example.ordermanagementcake.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ordermanagementcake.ui.clients.ClientsListScreen
import com.example.ordermanagementcake.ui.components.AppTopBar
import com.example.ordermanagementcake.ui.components.BottomNavigationBar
import com.example.ordermanagementcake.ui.orders.OrderListScreen

object Routes {
    const val ORDERS = "orders"
    const val CLIENTS = "clients"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedItem = when (currentRoute) {
        Routes.ORDERS -> 1
        Routes.CLIENTS -> 2
        else -> 2
    }

    Scaffold(
        topBar = {
            // Top bar agora iah ne'e deit atu dune'e kada pagina nia top bar hanesan hotu
            AppTopBar()
        },
        // no ba bottom bar nian mos halo componenet ketak ida deit iha ne
        bottomBar = {
            // one single bottom bar for the whole app
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    when (index) {
                        1 -> navController.navigate(Routes.ORDERS)
                        2 -> navController.navigate(Routes.CLIENTS)
                    }
                }
            )
        }
    ) { paddingValues ->
        // only this part changes when you navigate
        NavHost(
            navController = navController,
            startDestination = Routes.CLIENTS,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.ORDERS) {
                OrderListScreen()  // no navController needed anymore
            }
            composable(Routes.CLIENTS) {
                ClientsListScreen()  // no navController needed anymore
            }
        }
    }
}
