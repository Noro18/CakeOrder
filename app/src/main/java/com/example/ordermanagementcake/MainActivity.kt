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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.navigation.compose.rememberNavController
import com.example.ordermanagementcake.data.local.OrderDatabase
import com.example.ordermanagementcake.data.repository.ClientRepository
import com.example.ordermanagementcake.data.repository.OrderRepository
import com.example.ordermanagementcake.ui.clients.ClientViewModel
import com.example.ordermanagementcake.ui.clients.ClientViewModelFactory
import com.example.ordermanagementcake.ui.clients.ClientsListScreen
import com.example.ordermanagementcake.ui.navigation.AppNavHost
import com.example.ordermanagementcake.ui.navigation.Routes
import com.example.ordermanagementcake.ui.orders.OrderViewModel
import com.example.ordermanagementcake.ui.orders.OrderViewModelFactory
import com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = OrderDatabase.getInstance(this)

        val orderViewModel: OrderViewModel by viewModels {
            OrderViewModelFactory(OrderRepository(db.orderDao()))
        }
        val clientViewModel: ClientViewModel by viewModels {
            ClientViewModelFactory(ClientRepository(db.clientDao()))
        }

        setContent {
            OrderManagementCakeTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    orderViewModel = orderViewModel,
                    clientViewModel = clientViewModel

                    )


            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    OrderManagementCakeTheme {
        val navController = rememberNavController()
        AppNavHost(
            navController = navController,
            startDestination = Routes.ORDERS  // 👈 just add this
        )
    }
}*/
