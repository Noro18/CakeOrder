package com.example.ordermanagementcake.ui.orderviewscren

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderScreen() {

    var client by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("New Order", color = Color.White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                },
                actions = {
                    Image(
                        painter = painterResource(id = R.drawable
                            .sell),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(34.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF7B3A10)
                )
            )
        },
        containerColor = Color(0xFFF0EDE8)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Card ORDER ENTRY
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Box(
                        Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFFFF3E0))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            "ORDER ENTRY",
                            color = Color(0xFFE8640C),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Craft a new creation",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Capture the details for your client's next celebration. Every field helps ensure a perfect bake.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(16.dp))

                    // CLIENT
                    Text(
                        "CLIENT",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                client.ifEmpty { "Select a Client" },
                                color = if (client.isEmpty()) Color.LightGray else Color.Black
                            )
                            Icon(Icons.Default.ArrowDropDown, null, tint = Color.Gray)
                        }
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf("Client A", "Client B", "Client C").forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = { client = it; expanded = false }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // CAKE NAME
                    Text(
                        "CAKE NAME",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 14.dp)
                    ) {
                        Text("e.g. Victorian Sponge", color = Color.LightGray)
                    }

                    Spacer(Modifier.height(12.dp))

                    // QUANTITY
                    Text(
                        "QUANTITY",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 14.dp)
                    ) {
                        Text("1", color = Color.Black)
                    }

                    Spacer(Modifier.height(12.dp))

                    // INITIAL STATUS
                    Text(
                        "INITIAL STATUS",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFF8F0), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Pending", color = Color.Black)
                        Icon(
                            Icons.Default.DateRange,
                            null,
                            tint = Color(0xFFE8640C),
                            modifier = Modifier.size(20.dp)
                        )
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
                        Icon(
                            Icons.Default.DateRange,
                            null,
                            tint = Color(0xFFE8640C),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text("Timeline", fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "ORDER DATE",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 14.dp)
                    ) {
                        Text("mm/dd/yyyy", color = Color.LightGray)
                    }
                }
            }

            // Card NOTES
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "NOTES / CAKE INSCRIPTION",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Write the personalized message or special dietary requirements here...",
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                }
            }

            // Reference Photo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFC8A882)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.DateRange,
                        null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text("Reference Photo", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            // Tombol Save
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8640C))
            ) {
                Icon(Icons.Default.CheckCircle, null, tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Save Order", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewOrderScreenPreview() {
    NewOrderScreen()
}