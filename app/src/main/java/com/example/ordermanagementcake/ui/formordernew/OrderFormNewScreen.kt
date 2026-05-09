package com.example.ordermanagementcake.ui.formordernew

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderForm() {
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text  = "Order Form",
                        fontWeight = FontWeight.Bold
                ) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Card: Search and Selected Client
            Card(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(360.dp)
                    .height(230.dp),
                shape = RoundedCornerShape(16.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Box(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp)
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Profile",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White
                                )
                            }
                        }
                        Text(
                            text = "La iha Cliente Selecionado",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp, start = 12.dp),
                            lineHeight = 28.sp
                        )
                    }

                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        placeholder = {
                            Text(
                                text = "buka clinet ne'ebe eziste...",
                                color = Color(0xFF8C280E)
                            ) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color(0xFF8C280E)
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    // Add New Client Card inside the main card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF96340F)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { /* Handle add new client */ }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = "Add",
                                tint = Color(0xFFFCF9F8)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Adisiona Kliente Foun",
                                color = Color(0xFFF6F0EF),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(25.dp))
            // cake items row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "contem order sira",
                        color = Color(0xFF8C280E)
                    )

                    Text(
                        text = "Elemento Cake",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 2.dp),
                        fontSize = 25.sp,
                        color = Color(0xFF8C280E)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "draft order",
                        color = Color.Gray,
                        modifier = Modifier
                            .clickable { /* Handle change */ }
                    )

                    Text(
                        text = "$0.00",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color(0xFF8C280E),
                        modifier = Modifier
                            .clickable { /* Handle change */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Dashed Border "Add First Cake" Section
            val stroke = Stroke(
                width = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(140.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = Color.LightGray,
                            style = stroke,
                            cornerRadius = CornerRadius(12.dp.toPx())
                        )
                    }
                    .clickable { /* Handle add first cake */ },
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
                            .background(Color(0xFFF3F3F3)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color(0xFF8C280E),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Aumenta Cake Primeiro",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Select from your menu or create custom",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Delivery Details Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "DELIVERY DETAILS",
                    color = Color(0xFF8C280E),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable { /* Handle selection */ },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
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
                            text = "Select pickup or delivery date",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Calendar",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrderFormPreview (){
    OrderForm()
}
