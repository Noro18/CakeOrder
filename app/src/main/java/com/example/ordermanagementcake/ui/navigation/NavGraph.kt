package com.example.ordermanagementcake.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonAdd
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ordermanagementcake.data.local.entities.ClientEntity
import com.example.ordermanagementcake.ui.clients.ClientDetail
import com.example.ordermanagementcake.ui.clients.ClientViewModel
import com.example.ordermanagementcake.ui.clients.ClientsListScreen
import com.example.ordermanagementcake.ui.components.AppDrawer
import com.example.ordermanagementcake.ui.components.AppTopBar
import com.example.ordermanagementcake.ui.components.BottomNavigationBar
import com.example.ordermanagementcake.ui.dashboard.DashboardScreen
import com.example.ordermanagementcake.ui.forms.clients.NewClientForm
import com.example.ordermanagementcake.ui.forms.orders.NewOrderForm
import com.example.ordermanagementcake.ui.orders.OrderListScreen
import com.example.ordermanagementcake.ui.forms.orders.NewOrderScreen
import com.example.ordermanagementcake.ui.orders.OrderViewModel
import com.example.ordermanagementcake.ui.schedule.ScheduleViewScreen
import kotlinx.coroutines.launch

object Routes {
    const val DASHBOARD = "dashboard"
    const val ORDERS = "orders"
    const val CLIENTS = "clients"
    const val SCHEDULES = "schedules"

    const val NEW_ORDER = "new_order"
    const val NEW_CLIENT = "new_client"
    const val DETAIL_CLIENT = "client_detail/{clientID}" // route based on

    fun clientDetail(clientId: Int) = "client_detail/$clientId"

}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Routes.DASHBOARD,
    orderViewModel: OrderViewModel,
    clientViewModel: ClientViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedItem = when (currentRoute) {
        Routes.DASHBOARD -> 0
        Routes.ORDERS    -> 1
        Routes.CLIENTS   -> 2
        Routes.SCHEDULES -> 3
        else             -> 1
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // ✅ Use currentValue, not isOpen — avoids the animation race condition
    val isDrawerOpen = drawerState.currentValue == DrawerValue.Open
    BackHandler(enabled = isDrawerOpen) {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(onClose = { scope.launch { drawerState.close() } })
        }
    ) {
        Scaffold(
            topBar = {
                AppTopBar(onMenuClick = { scope.launch { drawerState.open() } })
            },
            bottomBar = {
                BottomNavigationBar(
                    selectedItem = selectedItem,
                    onItemSelected = { index ->
                        val route = when (index) {
                            0 -> Routes.DASHBOARD
                            1 -> Routes.ORDERS
                            2 -> Routes.CLIENTS
                            3 -> Routes.SCHEDULES
                            else -> Routes.DASHBOARD
                        }
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            },
            floatingActionButton = {
                data class FabConfig(
                    val icon: ImageVector,
                    val description: String,
                    val route: String
                )

                val fabConfig = when (currentRoute) {
                    Routes.ORDERS  -> FabConfig(Icons.Default.AddShoppingCart, "New Order",  Routes.NEW_ORDER)
                    Routes.CLIENTS -> FabConfig(Icons.Default.PersonAdd,       "New Client", Routes.NEW_CLIENT)
                    else           -> null
                }

                fabConfig?.let { config ->
                    FloatingActionButton(
                        onClick = { navController.navigate(config.route) },
                        containerColor = Color(0xFFC23C12)
                    ) {
                        Icon(config.icon, contentDescription = config.description, tint = Color.White)
                    }
                }
            }
        ) { paddingValues ->
            // only this part changes when you navigate
            // AFTER — quick 150ms fade
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues),
                enterTransition = { fadeIn(animationSpec = tween(150)) },
                exitTransition = { fadeOut(animationSpec = tween(150)) },
                popEnterTransition = { fadeIn(animationSpec = tween(150)) },
                popExitTransition = { fadeOut(animationSpec = tween(150)) }
            ) {
                composable(Routes.ORDERS)    { OrderListScreen(orderViewModel) }
                composable(Routes.CLIENTS)   { ClientsListScreen(
                    clientViewModel,
                    onClientClick = { clientId ->
                        navController.navigate(Routes.clientDetail(clientId))

                    }) }
                composable(Routes.DASHBOARD) { DashboardScreen() }
                composable(Routes.SCHEDULES) { ScheduleViewScreen() }
                composable(
                    route = Routes.NEW_ORDER,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(300)) +
                                fadeIn(tween(300))
                    },
                    exitTransition = { fadeOut(tween(200)) },
                    popEnterTransition = { EnterTransition.None },
                    popExitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(300)) +
                                fadeOut(tween(300))
                    }
                ) {
                    NewOrderForm(onAddNewClient = { navController.navigate(Routes.NEW_CLIENT) })
                }
                composable(route = Routes.NEW_CLIENT) {
                    NewClientForm(
                        onDismiss = { navController.popBackStack() },  // ← was onBack
                        onSave = { name, phone, address, notes ->
                            clientViewModel.insertClient(
                                ClientEntity(
                                    name = name.trim(),
                                    phone = phone.trim(),
                                    address = address.trim(),
                                    notes = notes.trim()
                                )
                            )
                            navController.popBackStack()
                        }
                    )
                }
                composable(route = Routes.DETAIL_CLIENT) {
                    ClientDetail (

                    ) {  }
                }
            }
        }
    }
}

@Composable
fun Open() {
    TODO("Not yet implemented")
}
