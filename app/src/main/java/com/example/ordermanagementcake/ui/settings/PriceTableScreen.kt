package com.example.ordermanagementcake.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ordermanagementcake.ui.components.AppTopBarMuted

@Composable
fun PriceTableScreen(
    viewModel: PriceTableViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Shape", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Size (inch)", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Price", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                }
                HorizontalDivider()
            }

            items(uiState.prices) { entry ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(entry.shapeName, modifier = Modifier.weight(1f))
                    Text("${entry.sizeInches}\"", modifier = Modifier.weight(1f))
                    Text("$${String.format("%.2f", entry.price)}", modifier = Modifier.weight(1f))
                }
                HorizontalDivider(modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}
