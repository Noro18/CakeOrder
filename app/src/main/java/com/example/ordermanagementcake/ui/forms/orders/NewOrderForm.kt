package com.example.ordermanagementcake.ui.forms.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ordermanagementcake.data.draft.CakeDraft
import com.example.ordermanagementcake.ui.orders.NewOrderViewModel
import com.example.ordermanagementcake.ui.theme.extendedColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.derivedStateOf
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderForm(
    viewModel: NewOrderViewModel,
    onAddNewClient: () -> Unit = {},
    onSaveOrder: () -> Unit = {},
    onNewCake: () -> Unit = {}
) {
    val draft = viewModel.orderDraft
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val clientSuggestions by viewModel.clientSuggestions.collectAsStateWithLifecycle()

    // 1. Formatters
    val dbFormatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US) }
    val dbDateOnlyFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.US) }
    val uiDateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    val uiTimeFormatter = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    // 2. States for Date/Time Pickers
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 0,
        is24Hour = true
    )

    // Derived display values
    val displayDate = remember(draft.deliveryDate) {
        if (draft.deliveryDate.isEmpty()) "Hili Data"
        else {
            try {
                val date = dbFormatter.parse(draft.deliveryDate) ?: Date()
                uiDateFormatter.format(date)
            } catch (e: Exception) {
                draft.deliveryDate // Fallback if format is weird
            }
        }
    }

    val displayTime = remember(draft.deliveryDate) {
        if (draft.deliveryDate.isEmpty() || !draft.deliveryDate.contains(" ")) "Hili Oras"
        else {
            try {
                val date = dbFormatter.parse(draft.deliveryDate) ?: Date()
                uiTimeFormatter.format(date)
            } catch (e: Exception) {
                "Hili Oras"
            }
        }
    }
    
    // Explicit control for the dropdown
    var expanded by remember { mutableStateOf(false) }
    var showNoClientWarning by remember { mutableStateOf(false) }

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val selectedDateStr = dbDateOnlyFormatter.format(Date(millis))
                        // Keep existing time if present, otherwise default to 12:00
                        val existingTime = if (draft.deliveryDate.contains(" ")) {
                            draft.deliveryDate.substringAfter(" ")
                        } else "12:00"
                        
                        viewModel.updateOrderDraft { 
                            it.copy(deliveryDate = "$selectedDateStr $existingTime") 
                        }
                    }
                    showDatePicker = false
                }) {
                    Text("OK", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("CANCEL", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Time Picker Dialog
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val hour = String.format("%02d", timePickerState.hour)
                    val minute = String.format("%02d", timePickerState.minute)
                    val selectedTimeStr = "$hour:$minute"
                    
                    // Keep existing date if present, otherwise default to today
                    val existingDate = if (draft.deliveryDate.contains(" ")) {
                        draft.deliveryDate.substringBefore(" ")
                    } else if (draft.deliveryDate.isNotEmpty() && !draft.deliveryDate.contains(",")) {
                        draft.deliveryDate // might be just the date part
                    } else {
                        dbDateOnlyFormatter.format(Date())
                    }
                    
                    viewModel.updateOrderDraft { 
                        it.copy(deliveryDate = "$existingDate $selectedTimeStr") 
                    }
                    showTimePicker = false
                }) {
                    Text("OK", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("CANCEL", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TimePicker(state = timePickerState)
                }
            }
        )
    }

    if (showNoClientWarning) {
        AlertDialog(
            onDismissRequest = { showNoClientWarning = false },
            confirmButton = {
                TextButton(onClick = { showNoClientWarning = false }) {
                    Text("OK", color = MaterialTheme.colorScheme.primary)
                }
            },
            title = {
                Text(text = "Atenasaun", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = "Favor hili kliente ida antes rai pedidu.")
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.extendedColors.surfaceContainerLow
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp)
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.extendedColors.surfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = if (draft.clientName.isNullOrEmpty()) "La iha Cliente Selecionado" else draft.clientName,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp, start = 12.dp),
                            lineHeight = 28.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    ExposedDropdownMenuBox(
                        expanded = expanded && clientSuggestions.isNotEmpty(),
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { 
                                expanded = true
                                viewModel.onClientSearchQueryChange(it) 
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            placeholder = {
                                Text(
                                    text = "buka clinet ne'ebe eziste...",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            trailingIcon = {
                                if (draft.clientId != null) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Selected",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        ExposedDropdownMenu(
                            expanded = expanded && clientSuggestions.isNotEmpty(),
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        ) {
                            clientSuggestions.forEach { client ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(text = client.name, fontWeight = FontWeight.Bold)
                                            Text(text = client.phone, style = MaterialTheme.typography.bodySmall)
                                        }
                                    },
                                    onClick = { 
                                        viewModel.selectClient(client)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAddNewClient() }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = "Add",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Adisiona Kliente Foun",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "contem order sira",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Elemento Cake",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 2.dp),
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "draft order",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable { }
                    )
                    Text(
                        text = "$${"%.2f".format(draft.totalPrice)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (draft.cakes.isEmpty()) {
                val stroke = Stroke(
                    width = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
                val dashBorderColor = MaterialTheme.colorScheme.outlineVariant

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = dashBorderColor,
                                style = stroke,
                                cornerRadius = CornerRadius(12.dp.toPx())
                            )
                        }
                        .clickable { onNewCake() },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.extendedColors.surfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Aumenta Cake Primeiro",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Select from your menu or create custom",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    draft.cakes.forEachIndexed { index, cake ->
                        CakeDraftItem(cake, onDelete = { viewModel.removeCakeFromDraft(index) })
                    }
                    Button(
                        onClick = onNewCake,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Text("Aumenta Cake Seluk", color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "DETALLES ENTREGA",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Date Card
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clickable { showDatePicker = true },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.extendedColors.surfaceContainerLow
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = displayDate,
                                color = if (displayDate == "Hili Data")
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                else MaterialTheme.colorScheme.onSurface,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Calendar",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Time Card
                    Card(
                        modifier = Modifier
                            .weight(0.7f)
                            .height(56.dp)
                            .clickable { showTimePicker = true },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.extendedColors.surfaceContainerLow
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = displayTime,
                                color = if (displayTime == "Hili Oras")
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                else MaterialTheme.colorScheme.onSurface,
                                fontSize = 14.sp
                            )
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = "Time",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "ORDER NOTES",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = draft.orderNotes,
                    onValueChange = { newNotes -> viewModel.updateOrderDraft { it.copy(orderNotes = newNotes) }},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Adisiona nota ruma ba enkomenda ne'e...",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    minLines = 4,
                    maxLines = 6,
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.extendedColors.surfaceContainerLow,
                        unfocusedContainerColor = MaterialTheme.extendedColors.surfaceContainerLow,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 16.dp,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (draft.clientId == null) {
                            showNoClientWarning = true
                        } else {
                            viewModel.saveOrder(onSuccess = onSaveOrder)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Rai Pedidu",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun CakeDraftItem(cake: CakeDraft, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.extendedColors.surfaceContainerLow)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = cake.cakeTitle, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(text = "${cake.tiers.size} Nívél", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
