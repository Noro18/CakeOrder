package com.example.ordermanagementcake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.ordermanagementcake.data.local.OrderDatabase
import com.example.ordermanagementcake.data.repository.CakeRepository
import com.example.ordermanagementcake.data.repository.ClientRepository
import com.example.ordermanagementcake.data.repository.OrderRepository
import com.example.ordermanagementcake.data.repository.PriceTableRepository
import com.example.ordermanagementcake.data.repository.ShapeRepository
import com.example.ordermanagementcake.data.repository.SizeRepository
import com.example.ordermanagementcake.data.repository.TierRepository
import com.example.ordermanagementcake.ui.clients.ClientViewModel
import com.example.ordermanagementcake.ui.clients.ClientViewModelFactory
import com.example.ordermanagementcake.ui.clients.ClientsListScreen
import com.example.ordermanagementcake.ui.navigation.AppNavHost
import com.example.ordermanagementcake.ui.navigation.Routes
import com.example.ordermanagementcake.ui.orders.NewOrderViewModel
import com.example.ordermanagementcake.ui.orders.NewOrderViewModelFactory
import com.example.ordermanagementcake.ui.orders.OrderViewModel
import com.example.ordermanagementcake.ui.orders.OrderViewModelFactory
import com.example.ordermanagementcake.ui.schedule.ScheduleViewModel
import com.example.ordermanagementcake.ui.schedule.ScheduleViewModelFactory
import com.example.ordermanagementcake.ui.schedule.ScheduleViewScreen
import com.example.ordermanagementcake.ui.settings.PriceTableViewModel
import com.example.ordermanagementcake.ui.settings.PriceTableViewModelFactory
import com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme
import kotlin.getValue

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, notifications will work
        } else {
            // Permission denied, notifications won't work
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        askNotificationPermission()

        val notificationHelper = com.example.ordermanagementcake.notifications.NotificationHelper(this)
        notificationHelper.createNotificationChannel()

        val db = OrderDatabase.getInstance(this)

        val orderViewModel: OrderViewModel by viewModels {
            OrderViewModelFactory(OrderRepository(db.orderDao()))
        }
        val clientViewModel: ClientViewModel by viewModels {
            ClientViewModelFactory(ClientRepository(db.clientDao()))
        }

        val priceTableViewModel: PriceTableViewModel by viewModels {
            PriceTableViewModelFactory(
                PriceTableRepository(db.priceTableDao()),
                ShapeRepository(db.shapeDao()),
                SizeRepository(db.sizeDao())
            )
        }

        val scheduleViewModel: ScheduleViewModel by viewModels {
            ScheduleViewModelFactory(OrderRepository(db.orderDao()))
        }

        val newOrderViewModel: NewOrderViewModel by viewModels {
            NewOrderViewModelFactory(
                OrderRepository(db.orderDao()),
                CakeRepository(db.cakeDao()),
                TierRepository(db.tierDao()),
                ShapeRepository(db.shapeDao()),
                SizeRepository(db.sizeDao()),
                ClientRepository(db.clientDao()),
                PriceTableRepository(db.priceTableDao()),
                com.example.ordermanagementcake.notifications.AlarmSchedulerImpl(this)
            )
        }



        setContent {
            val sharedPreferences = remember { getSharedPreferences("app_settings", MODE_PRIVATE) }
            var themeMode by remember {
                mutableStateOf(sharedPreferences.getString("theme_mode", "Sistems default") ?: "Sistems default")
            }

            val darkTheme = when (themeMode) {
                "Naroman" -> false
                "Nakukun" -> true
                else -> androidx.compose.foundation.isSystemInDarkTheme()
            }

            OrderManagementCakeTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    orderViewModel = orderViewModel,
                    clientViewModel = clientViewModel,
                    scheduleViewModel = scheduleViewModel,
                    newOrderViewModel = newOrderViewModel,
                    priceTableViewModel = priceTableViewModel,
                    currentTheme = themeMode,
                    onThemeChange = { newTheme ->
                        themeMode = newTheme
                        sharedPreferences.edit().putString("theme_mode", newTheme).apply()
                    }
                )
            }
        }
    }
}


