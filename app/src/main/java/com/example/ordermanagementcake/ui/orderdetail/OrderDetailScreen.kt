package com.example.ordermanagementcake.ui.orderdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.R
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Screen foun ba Detallu Pedidu (New Order Detail screen design).
 * Hatudu informasaun kompletu kona-ba pedidu #BK-8842.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen() {
    // Definisaun kór sira (Color definitions)
    val mainOrange = Color(0xFFF37B21)
    val textBrown = Color(0xFF8C280E)
    val bgColor = MaterialTheme.colorScheme.background
    val cardBg = MaterialTheme.colorScheme.surface
    val statusBg = Color(0xFFF6E6D9)

    // Estadu ba nota husi baker (State for baker's notes)
    var bakersNotes by remember { mutableStateOf("") }

    // Estadu ba data sira (States for dates)
    var bakingDate by remember { mutableStateOf("Out 24, 2023") }
    var pickupDate by remember { mutableStateOf("Out 26, 2023") }

    // Estadu ba Date Picker (State for showing Date Pickers)
    var showBakingDatePicker by remember { mutableStateOf(false) }
    var showPickupDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    // Dialog ba hili Data Te'in (Baking Date Picker Dialog)
    if (showBakingDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showBakingDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        bakingDate = sdf.format(Date(it))
                    }
                    showBakingDatePicker = false
                }) {
                    Text("RAI", color = textBrown)
                }
            },
            dismissButton = {
                TextButton(onClick = { showBakingDatePicker = false }) {
                    Text("KANSELA", color = Color.Gray)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Dialog ba hili Data Foti (Pickup Date Picker Dialog)
    if (showPickupDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showPickupDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        pickupDate = sdf.format(Date(it))
                    }
                    showPickupDatePicker = false
                }) {
                    Text("RAI", color = textBrown)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPickupDatePicker = false }) {
                    Text("KANSELA", color = Color.Gray)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalles Pedidu",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Fila ba fali */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = textBrown
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Hatudu menu */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = textBrown
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 16.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = cardBg
            ) {
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TOTÁL AMONTU",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "$195.00",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Button(
                        onClick = { /* Rai pedidu */ },
                        modifier = Modifier
                            .height(56.dp)
                            .width(180.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = mainOrange)
                    ) {
                        Text(
                            text = "Rai Pedidu",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        },
        containerColor = bgColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Seksaun ID Pedidu no Badge Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "PEDIDU AGORA",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "#BK-8842",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = textBrown
                    )
                }

                Surface(
                    color = statusBg,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.clickable { /* Troka status */ }
                ) {
                    Text(
                        text = "PENDENTE",
                        color = Color(0xFFD48259),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Card Detallu Kliente
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Detalles Kliente",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFFFEDE6)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color(0xFFD48259),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Elena Rodriguez",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Upper East Side, Manhattan",
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Seksaun Item Pedidu sira
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Item sira ne'ebé Pedidu",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "2 items",
                    fontSize = 12.sp,
                    color = textBrown,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            CakeOrderItem(
                name = "Midnight Ganache",
                description = "7\" Signature Collection",
                details = "3 Layers, 2 Tiers",
                price = "$85.00",
                imageRes = R.drawable.raspberrylayercake,
                onEditClick = { /* Edita item */ }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CakeOrderItem(
                name = "Vanilla Ruffle",
                description = "9\" Special Occasion",
                details = "2 Layers, 1 Tier",
                price = "$110.00",
                imageRes = R.drawable.raspberrylayercake,
                onEditClick = { /* Edita item */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card Timeline Produsaun ho botão sira hodi muda data
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Linha Tempu Produsaun",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Botão hodi muda Data Te'in (Baking Date button)
                    TimelineItem(
                        label = "DATA TE'IN",
                        date = bakingDate,
                        icon = Icons.Default.CalendarToday,
                        onClick = { showBakingDatePicker = true }
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Botão hodi muda Data Foti (Pickup Date button)
                    TimelineItem(
                        label = "DATA FOTI/ENTREGA",
                        date = pickupDate,
                        icon = Icons.Default.LocalShipping,
                        onClick = { showPickupDatePicker = true }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Seksaun Nota hosi Baker
            Text(
                text = "Nota hosi Baker",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = bakersNotes,
                onValueChange = { bakersNotes = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { 
                    Text(
                        "Hatama instrusaun espesiál ka detallu dekorasaun florál...",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    ) 
                },
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = cardBg,
                    focusedContainerColor = cardBg,
                    unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                    focusedBorderColor = mainOrange
                )
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CakeOrderItem(
    name: String,
    description: String,
    details: String,
    price: String,
    imageRes: Int,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = details,
                    fontSize = 11.sp,
                    color = Color.LightGray
                )
                Text(
                    text = price,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = Color(0xFF8C280E)
                )
            }
            
            Surface(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onEditClick() },
                color = Color(0xFFF5F5F5)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(16.dp),
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun TimelineItem(
    label: String,
    date: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { onClick() }
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = Color.Transparent
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color(0xFF8C280E),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = date,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderDetailScreenPreview(){
    OrderDetailScreen()
}
