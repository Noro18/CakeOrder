package com.example.ordermanagementcake.ui.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ordermanagementcake.R
import com.example.ordermanagementcake.data.local.OrderDatabase
import com.example.ordermanagementcake.data.repository.OrderRepository
import androidx.compose.foundation.Image

@Composable
fun OrderListScreen(
    viewModel: OrderViewModel = viewModel(
        factory = OrderViewModelFactory(
            OrderRepository(
                OrderDatabase.getInstance(LocalContext.current).orderDao()
            )
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val statusFilters = listOf("PENDING", "IN_PROGRESS", "READY", "COMPLETED")
    val statusLabels  = listOf("Hotu", "Hein", "Prosesu", "Prontu")

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Order sira iha agora",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 16.dp, start = 15.dp)
        )
        Text(
            text = "Jestiona ita-nia kriasaun kulinária",
            modifier = Modifier.padding(bottom = 7.dp, start = 15.dp)
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("buka order...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )

        // Filter buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 15.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            statusFilters.forEachIndexed { index, status ->
                val isSelected = uiState.selectedStatus == status
                Button(
                    onClick = { viewModel.loadOrders(status) },
                    modifier = Modifier.padding(horizontal = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color(0xFFF87146) else Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(statusLabels[index])
                }
            }
        }

        // Loading / error / list
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null -> {
                Text(
                    text = "Error: ${uiState.errorMessage}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            uiState.orders.isEmpty() -> {
                Text(
                    text = "La iha order ba status ne'e.",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray
                )
            }
            else -> {
                uiState.orders.forEach { order ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.double_chodolate_fudge),
                                contentDescription = "Order image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Order #${order.id}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = order.notes.ifBlank { "La iha nota" },
                                    fontSize = 15.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "Foti", fontSize = 15.sp)
                                Text(
                                    text = order.pickupDate,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { viewModel.updateStatus(order.id, "IN_PROGRESS") },
                                modifier = Modifier.height(35.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFEE8111),
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = order.status, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}