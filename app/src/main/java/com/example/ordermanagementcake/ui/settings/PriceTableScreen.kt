package com.example.ordermanagementcake.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ordermanagementcake.ui.components.AppTopBarMuted

@Composable
fun PriceTableScreen(
    viewModel: PriceTableViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedShapeId by remember { mutableStateOf<Int?>(null) }
    var selectedShapeName by remember { mutableStateOf("") }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pushed
            items(uiState.shapeGroups) { group ->
                ShapePriceCard(
                    group = group,
                    onAddPrice = { shapeId ->
                        selectedShapeId = shapeId
                        selectedShapeName = group.shapeName
                        showAddDialog = true
                    }
                )
            }
        }
    }

    if (showAddDialog && selectedShapeId != null) {
        AddPriceDialog(
            shapeName = selectedShapeName,
            onDismiss = { showAddDialog = false },
            onConfirm = { size, price ->
                viewModel.addPrice(selectedShapeId!!, size, price)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddPriceDialog(
    shapeName: String,
    onDismiss: () -> Unit,
    onConfirm: (Double, Double) -> Unit
) {
    var sizeText by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Price for $shapeName") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = sizeText,
                    onValueChange = { sizeText = it },
                    label = { Text("Size (inches)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Price ($)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val size = sizeText.toDoubleOrNull()
                    val price = priceText.toDoubleOrNull()
                    if (size != null && price != null) {
                        onConfirm(size, price)
                    }
                },
                enabled = sizeText.toDoubleOrNull() != null && priceText.toDoubleOrNull() != null
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ShapePriceCard(
    group: ShapeGroup,
    onAddPrice: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = group.shapeName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = { onAddPrice(group.shapeId) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Price for ${group.shapeName}",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = 4.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            if (group.prices.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No prices set for this shape",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                group.prices.forEach { entry ->
                    ListItem(
                        modifier = Modifier.clickable { /* TODO: Future use - edit price */ },
                        headlineContent = {
                            Text(
                                text = "${entry.sizeInches}\" Size",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        trailingContent = {
                            Text(
                                text = "$${String.format("%.2f", entry.price)}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = androidx.compose.ui.graphics.Color.Transparent
                        )
                    )
                }
            }
        }
    }
}
