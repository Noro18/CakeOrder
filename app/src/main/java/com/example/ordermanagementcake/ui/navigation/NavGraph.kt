package com.example.ordermanagementcake.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.example.ordermanagementcake.ui.components.AppTopBarDelete
import com.example.ordermanagementcake.ui.components.AppTopBarMuted
import com.example.ordermanagementcake.ui.components.AppTopBarNewOrder
import com.example.ordermanagementcake.ui.components.BottomNavigationBar
import com.example.ordermanagementcake.ui.dashboard.DashboardScreen
import com.example.ordermanagementcake.ui.dashboard.DashboardViewModel
import com.example.ordermanagementcake.ui.forms.cakes.NewCakeForm
import com.example.ordermanagementcake.ui.forms.clients.NewClientForm
import com.example.ordermanagementcake.ui.forms.orders.NewOrderForm
import com.example.ordermanagementcake.ui.orders.OrderListScreen
import com.example.ordermanagementcake.ui.forms.orders.NewOrderScreen
import com.example.ordermanagementcake.ui.forms.tier.NewTierForm
import com.example.ordermanagementcake.ui.orderdetail.OrderDetailScreen
import com.example.ordermanagementcake.ui.orders.NewOrderViewModel
import com.example.ordermanagementcake.ui.orders.OrderViewModel
import com.example.ordermanagementcake.ui.schedule.ScheduleViewModel
import com.example.ordermanagementcake.ui.schedule.ScheduleViewScreen
import com.example.ordermanagementcake.ui.settings.PriceTableScreen
import com.example.ordermanagementcake.ui.settings.PriceTableViewModel
import com.example.ordermanagementcake.ui.settings.SettingsScreen
import kotlinx.coroutines.launch

object Routes {
    const val DASHBOARD = "dashboard"
    const val ORDERS = "orders"
    const val CLIENTS = "clients"
    const val SCHEDULES = "schedules"

    const val NEW_ORDER = "new_order"
    const val NEW_CLIENT = "new_client"
    const val DETAIL_CLIENT = "client_detail/{clientID}" // route based on
    const val DETAIL_ORDER = "order_detail/{orderID}"
    const val NEW_CAKE = "new_cake"
    const val SETTINGS = "settings"
    const val PRICE_LIST = "price_list"
    const val EDIT_ORDER = "edit_order/{orderID}"


    fun clientDetail(clientId: Int) = "client_detail/$clientId"
    fun orderDetail(orderId: Int) = "order_detail/$orderId"
    fun editOrder(orderId: Int) = "edit_order/$orderId"

}

data class TopBarConfig (
    val title: String
)

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Routes.DASHBOARD,
    dashboardViewModel: DashboardViewModel,
    orderViewModel: OrderViewModel,
    clientViewModel: ClientViewModel,
    scheduleViewModel: ScheduleViewModel,
    newOrderViewModel: NewOrderViewModel,
    priceTableViewModel: PriceTableViewModel,
    currentTheme: String = "Sistems default",
    onThemeChange: (String) -> Unit = {}
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val ScreensWithOwnTopBar = listOf(
        Routes.DETAIL_CLIENT,
        Routes.DETAIL_ORDER,
        //Auenta routes seluk ne'ebe nia top bar iha hotu
    )
    // ROutes that uses the global topbar
    val showGlobalTopBar = currentRoute in listOf(
        Routes.DASHBOARD,
        Routes.ORDERS,
        Routes.CLIENTS,
        Routes.SCHEDULES,
        Routes.NEW_CLIENT
    )

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
            AppDrawer(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onSettingsClick = { navController.navigate(Routes.SETTINGS) },
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                if (showGlobalTopBar) {
                    AppTopBar(
                        title = when (currentRoute) {
                            Routes.DASHBOARD -> "Key's Cake Bakery"
                            Routes.ORDERS    -> "Pedidu"
                            Routes.CLIENTS   -> "Kliente"
                            Routes.SCHEDULES -> "Orario"
                            else             -> ""
                        },
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                } else if (currentRoute?.startsWith("client_detail/") == true) {
                    val clientUiState by clientViewModel.uiState.collectAsStateWithLifecycle()
                    var showDeleteDialog by remember { mutableStateOf(false) }

                    AppTopBarDelete(
                        title = "Detalhu Kliente",
                        onBackClick = { navController.popBackStack() },
                        onDeleteClick = { showDeleteDialog = true }
                    )

                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text("Konfirma Apaga") },
                            text = {
                                Text(
                                    "Ita boot hakarak delete kliente \"${clientUiState.selectedClient?.client?.name}\"?"
                                )
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    clientUiState.selectedClient?.client?.let { client ->
                                        clientViewModel.deleteClient(client)
                                        navController.popBackStack()
                                    }
                                    showDeleteDialog = false
                                }) {
                                    Text("Apaga", color = Color.Red)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Kansela")
                                }
                            }
                        )
                    }
                } else if (currentRoute == Routes.NEW_ORDER) {
                    AppTopBarNewOrder(onBackClick = { navController.popBackStack()}, title = "Pedidu Foun")
                } else if (currentRoute?.startsWith("edit_order/") == true) {
                    AppTopBarNewOrder(onBackClick = { navController.popBackStack()}, title = "Edita Pedidu")
                } else if (currentRoute == Routes.SETTINGS) {
                    AppTopBarMuted(onBackClick = { navController.popBackStack()}, title = "Configurasaun")
                }
            },
            bottomBar = {
                if (currentRoute in listOf(Routes.DASHBOARD, Routes.ORDERS, Routes.CLIENTS, Routes.SCHEDULES)) {
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
                }
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
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Icon(config.icon, contentDescription = config.description)
                    }
                }
            }
        ) { paddingValues ->
            // only this part changes when you navigate
            // AFTER — quick 150ms fade
            val screensWithOwnScaffold = listOf(
                Routes.DETAIL_ORDER,
                Routes.PRICE_LIST
            )
            val navPadding = if (currentRoute in screensWithOwnScaffold) PaddingValues(0.dp) else paddingValues

            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(navPadding)
            ) {
                composable(Routes.ORDERS)    { 
                    OrderListScreen(
                        viewModel = orderViewModel,
                        onOrderClick = { orderId ->
                            navController.navigate(Routes.orderDetail(orderId))
                        }
                    )
                }
                composable(Routes.CLIENTS)   {
                    var clientToEdit by remember { mutableStateOf<ClientEntity?>(null) }

                    ClientsListScreen(
                        clientViewModel,
                        onClientClick = { clientId ->
                            navController.navigate(Routes.clientDetail(clientId))
                        },
                        onEditClient = { client ->
                            clientToEdit = client
                        }
                    )

                    clientToEdit?.let { client ->
                        NewClientForm(
                            title = "Atualiza Kliente",
                            initialName = client.name,
                            initialPhone = client.phone,
                            initialAddress = client.address,
                            initialNotes = client.notes,
                            onDismiss = { clientToEdit = null },
                            onSave = { name, phone, address, notes ->
                                clientViewModel.updateClient(
                                    client.copy(
                                        name = name,
                                        phone = phone,
                                        address = address,
                                        notes = notes
                                    )
                                )
                                clientToEdit = null
                            }
                        )
                    }
                }
                composable(Routes.DASHBOARD) { DashboardScreen(
                    viewModel = dashboardViewModel,
                    onAddClient  = {
                        navController.navigate(Routes.NEW_CLIENT)
                    },
                    onAddOrder = {
                        navController.navigate(Routes.NEW_ORDER)
                    }
                ) }
                composable(Routes.SCHEDULES) { ScheduleViewScreen(scheduleViewModel) }
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
                    NewOrderForm(
                        viewModel = newOrderViewModel,
                        onAddNewClient = { navController.navigate(Routes.NEW_CLIENT) },
                        onNewCake = { navController.navigate(Routes.NEW_CAKE) },
                        onSaveOrder = {
                            navController.navigate(Routes.ORDERS) {
                                popUpTo(Routes.DASHBOARD)
                            }
                        }
                    )
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
                composable(route = Routes.DETAIL_CLIENT) { navBackStackEntry ->
                    val clientId = navBackStackEntry.arguments?.getString("clientID")?.toIntOrNull()

                    LaunchedEffect(clientId) {
                        clientId?.let { clientViewModel.loadClientDetail(it) }
                    }

                    val uiState by clientViewModel.uiState.collectAsStateWithLifecycle()

                    ClientDetail(
                        clientWithOrders = uiState.selectedClient,
                        onBackClick = { navController.popBackStack() },
                        onUpdateClient = { updatedClient ->
                            clientViewModel.updateClient(updatedClient)
                        }
                    )
                }
                composable(route = Routes.DETAIL_ORDER) { navBackStackEntry ->
                    val orderId = navBackStackEntry.arguments?.getString("orderID")?.toIntOrNull()

                    LaunchedEffect(orderId) {
                        orderId?.let { orderViewModel.loadOrderDetail(it) }
                    }

                    val uiState by orderViewModel.uiState.collectAsStateWithLifecycle()

                    OrderDetailScreen(
                        orderDetail = uiState.selectedOrderDetail,
                        onBackClick = { navController.popBackStack() },
                        onEditOrder = { id ->
                            navController.navigate(Routes.editOrder(id))
                        },
                        onStatusChange = { newStatus ->
                            orderId?.let { orderViewModel.updateStatus(it, newStatus) }
                        }
                    )
                }
                composable(route = Routes.EDIT_ORDER) { navBackStackEntry ->
                    val editOrderId = navBackStackEntry.arguments?.getString("orderID")?.toIntOrNull()

                    LaunchedEffect(editOrderId) {
                        editOrderId?.let { newOrderViewModel.initForEdit(it) }
                    }

                    NewOrderForm(
                        viewModel = newOrderViewModel,
                        onAddNewClient = { navController.navigate(Routes.NEW_CLIENT) },
                        onNewCake = { navController.navigate(Routes.NEW_CAKE) },
                        onSaveOrder = {
                            navController.popBackStack()
                        }
                    )
                }
                composable(route = Routes.NEW_CAKE) {
                    val editingCake = newOrderViewModel.getEditingCake()
                    NewCakeForm(
                        onSaveCake = { cakeDraft ->
                            newOrderViewModel.addOrUpdateCake(cakeDraft)
                            navController.popBackStack()
                        },
                        onBack = { navController.popBackStack() },
                        clientName = newOrderViewModel.orderDraft.clientName ?: "Kliente foun",
                        viewModel = newOrderViewModel,
                        initialCake = editingCake
                    )
                }
                composable(route = Routes.SETTINGS) {
                    SettingsScreen(
                        currentTheme = currentTheme,
                        onThemeChange = onThemeChange,
                        onPriceTableClick = { navController.navigate(Routes.PRICE_LIST) }
                    )
                }
                composable(route = Routes.PRICE_LIST) {
                    PriceTableScreen(
                        viewModel = priceTableViewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun Open() {
    TODO("Not yet implemented")
}
