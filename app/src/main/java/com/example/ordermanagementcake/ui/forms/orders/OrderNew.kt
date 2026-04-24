package com.example.ordermanagementcake.ui.forms.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderScreen() {
    // Form States
    var client by remember { mutableStateOf("") }
    var cakeName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    var orderDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // UI States
    var clientExpanded by remember { mutableStateOf(false) }
    var quantityExpanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        orderDate = formatter.format(Date(it))
                    }
                    showDatePicker = false
                }) { Text("OK", color = Color(0xFFE8640C)) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0EDE8))
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Card ORDER ENTRY
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Header
                Column {
                    Surface(
                        color = Color(0xFFFFF3E0),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            "ORDER ENTRY",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            color = Color(0xFFE8640C),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("Craft a new creation", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                    Text(
                        "Capture the details for your client's next celebration.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }

                // CLIENT DROPDOWN
                Column {
                    LabelText("CLIENT")
                    ExposedDropdownMenuBox(
                        expanded = clientExpanded,
                        onExpandedChange = { clientExpanded = !clientExpanded }
                    ) {
                        OutlinedTextField(
                            value = client,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("Select a Client") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = clientExpanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = RoundedCornerShape(8.dp),
                            colors = textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = clientExpanded,
                            onDismissRequest = { clientExpanded = false }
                        ) {
                            listOf("Client A", "Client B", "Client C").forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = { client = it; clientExpanded = false }
                                )
                            }
                        }
                    }
                }

                // CAKE NAME FIELD
                Column {
                    LabelText("CAKE NAME")
                    OutlinedTextField(
                        value = cakeName,
                        onValueChange = { cakeName = it },
                        placeholder = { Text("e.g. Victorian Sponge") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = textFieldColors()
                    )
                }

                // QUANTITY DROPDOWN
                Column {
                    LabelText("QUANTITY")
                    ExposedDropdownMenuBox(
                        expanded = quantityExpanded,
                        onExpandedChange = { quantityExpanded = !quantityExpanded }
                    ) {
                        OutlinedTextField(
                            value = quantity,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = quantityExpanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = RoundedCornerShape(8.dp),
                            colors = textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = quantityExpanded,
                            onDismissRequest = { quantityExpanded = false }
                        ) {
                            (1..5).forEach { num ->
                                DropdownMenuItem(
                                    text = { Text(num.toString()) },
                                    onClick = { quantity = num.toString(); quantityExpanded = false }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Card TIMELINE
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DateRange, null, tint = Color(0xFFE8640C), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Timeline", fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(12.dp))
                LabelText("ORDER DATE")
                OutlinedTextField(
                    value = orderDate,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select Date") },
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, null, tint = Color(0xFFE8640C))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = textFieldColors()
                )
            }
        }

        // Card NOTES
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Notes, null, tint = Color(0xFFE8640C), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Notes", fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = { Text("Write personalized message or special requirements...") },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = textFieldColors()
                )
            }
        }

        // Save Button
        Button(
            onClick = { /* Save Logic */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8640C))
        ) {
            Icon(Icons.Default.CheckCircle, null, tint = Color.White, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Save Order", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun LabelText(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        color = Color.Gray,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFFE8640C),
    unfocusedBorderColor = Color(0xFFE0E0E0),
    focusedLabelColor = Color(0xFFE8640C),
    cursorColor = Color(0xFFE8640C)
)

@Preview(showBackground = true)
@Composable
fun NewOrderScreenPreview() {
    NewOrderScreen()
}
