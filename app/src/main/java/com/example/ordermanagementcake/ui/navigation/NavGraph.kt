package com.example.ordermanagementcake.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ordermanagementcake.ui.clients.ClientsListScreen
import com.example.ordermanagementcake.ui.components.AppDrawer
import com.example.ordermanagementcake.ui.components.AppTopBar
import com.example.ordermanagementcake.ui.components.BottomNavigationBar
import com.example.ordermanagementcake.ui.dashboard.DashboardScreen
import com.example.ordermanagementcake.ui.orders.OrderListScreen
import com.example.ordermanagementcake.ui.forms.orders.NewOrderScreen
import com.example.ordermanagementcake.ui.schedule.ScheduleViewScreen
import kotlinx.coroutines.launch

object Routes {
    const val DASHBOARD = "dashboard"
    const val ORDERS = "orders"
    const val CLIENTS = "clients"
    const val SCHEDULES = "schedules"

    const val NEW_ORDER = "new_order"

}

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = Routes.DASHBOARD) { // screen primeiro ne'eb sei loke
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedItem = when (currentRoute) {
        Routes.DASHBOARD -> 0
        Routes.ORDERS -> 1
        Routes.CLIENTS -> 2
        Routes.SCHEDULES -> 3
        else -> 1
    }

    // drawer States
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // ne object ida ne'ebe remember drawer agora loke ga closed hela
    val scope = rememberCoroutineScope() // not clear but ne atu run opeing animation iha background

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer (
                onClose = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                // Top bar agora iah ne'e deit atu dune'e kada pagina nia top bar hanesan hotu
                AppTopBar(
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            },
            // no ba bottom bar nian mos halo componenet ketak ida deit iha ne
            bottomBar = {
                // one single bottom bar for the whole app
                BottomNavigationBar(
                    selectedItem = selectedItem,
                    onItemSelected = { index ->
                        when (index) {
                            0 -> navController.navigate(Routes.DASHBOARD)
                            1 -> navController.navigate(Routes.ORDERS)
                            2 -> navController.navigate(Routes.CLIENTS)
                            3 -> navController.navigate(Routes.SCHEDULES)

                        }
                    }
                )
            },
            floatingActionButton = {
                // Only show FAB on pages you want
                val showFab = currentRoute in listOf(Routes.ORDERS, Routes.CLIENTS)
                if (showFab) {
                    FloatingActionButton(
                        onClick = { navController.navigate(Routes.NEW_ORDER) },
                        containerColor = Color(0xFFC23C12)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                }
            }
        ) { paddingValues ->
            // only this part changes when you navigate
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Routes.ORDERS) {
                    OrderListScreen()  // no navController needed anymore
                }
                composable(Routes.CLIENTS) {
                    ClientsListScreen()  // no navController needed anymore
                }
                composable(Routes.DASHBOARD) {
                    DashboardScreen()
                }
                composable(Routes.SCHEDULES) {
                    ScheduleViewScreen()
                }
                composable(Routes.NEW_ORDER) {
                    NewOrderScreen()
                }
            }
        }
    }


}

@Composable
fun Open() {
    TODO("Not yet implemented")
}
