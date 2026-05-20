package com.example.ordermanagementcake.ui.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetail(
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    
    // Form States
    var name by remember { mutableStateOf("Eleanor Fitzgerald") }
    var phone by remember { mutableStateOf("+1 (555) 234-8890") }
    var address by remember { mutableStateOf("72 Oakwood Crescent, Maplewood Heights, NY 10012") }
    var notes by remember { mutableStateOf("Gosta menus frosting iha cupcake. Gosta baunilha husi Madagaskar. Uma laiha ai-fuan katar ba bei-oan sira.") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditing) "Edit Kliente" else name,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { if (isEditing) isEditing = false else onBackClick() }) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Close else Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = if (isEditing) "Kansela" else "Fila",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (!isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                        }
                        IconButton(onClick = onDeleteClick) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
                        }
                    }
                    // Profile Image
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Perfil",
                            modifier = Modifier.align(Alignment.Center),
                            tint = Color.Gray
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8B4513)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F8F8))
                .imePadding() // This is crucial for handling the keyboard
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Client Profile Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PERFIL KLIENTE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Text(
                    text = "ID: #BC-8821",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Editable Information Fields
            EditableInfoField(
                label = "NARAN KOMPLETU",
                value = name,
                onValueChange = { name = it },
                isEditing = isEditing
            )
            EditableInfoField(
                label = "NÚMERU TELEFONE",
                value = phone,
                onValueChange = { phone = it },
                isEditing = isEditing,
                icon = Icons.Default.Phone
            )
            EditableInfoField(
                label = "HELA FATIN",
                value = address,
                onValueChange = { address = it },
                isEditing = isEditing,
                icon = Icons.Default.LocationOn
            )
            EditableInfoField(
                label = "NOTA & PREFERÉNSIA PADARIA",
                value = notes,
                onValueChange = { notes = it },
                isEditing = isEditing,
                icon = Icons.Default.Description,
                isItalic = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = { 
                    isEditing = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7F27)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isEditing) "Rai Mudansa" else "Rai Informasaun Kliente",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                }
            }

            if (!isEditing) {
                Spacer(modifier = Modifier.height(32.dp))

                // Order History Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Istóriku Enkomenda",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Surface(
                        color = Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Totál Enkomenda 3",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OrderItem(
                    title = "Bolu Selebrasaun Triple Berry",
                    date = "OUT 14",
                    time = "Foti iha tuku 11:30 AM",
                    price = "$85.00",
                    status = "KONKLUIDU",
                    statusColor = Color(0xFF4CAF50)
                )
                OrderItem(
                    title = "Kaixa Macaron Signatura (24)",
                    date = "NOV 02",
                    time = "Entrega Horáriu",
                    price = "$45.00",
                    status = "HEIN HELA",
                    statusColor = Color(0xFFFF9800)
                )
                OrderItem(
                    title = "Lembransa Kazamentu Custom",
                    date = "AGO 20",
                    time = "Entrega ona ba Fatin",
                    price = "$210.00",
                    status = "KONKLUIDU",
                    statusColor = Color(0xFF9E9E9E)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Haree Istóriku Enkomenda Detalladu",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color(0xFF8B4513),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom Actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionButton(icon = Icons.Default.Add, label = "Enkomenda Foun")
                    ActionButton(icon = Icons.Default.Email, label = "Kontaktu")
                }
            }
        }
    }
}

@Composable
fun EditableInfoField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditing: Boolean,
    icon: ImageVector? = null,
    isItalic: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Top) {
                if (icon != null) {
                    Icon(
                        icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp).padding(top = 2.dp),
                        tint = Color(0xFF8B4513)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                
                if (isEditing) {
                    OutlinedTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF8B4513),
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                } else {
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OrderItem(title: String, date: String, time: String, price: String, status: String, statusColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF0F0F0))
            ) {
                Icon(Icons.Default.Cake, contentDescription = null, modifier = Modifier.align(Alignment.Center), tint = Color.LightGray)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = date.split(" ")[0], fontSize = 10.sp, color = Color.Gray)
                        Text(text = date.split(" ")[1], fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Text(text = time, fontSize = 12.sp, color = Color.Gray)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                    Surface(
                        color = statusColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = status,
                            color = statusColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(text = price, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF8B4513))
                }
            }
        }
    }
}

@Composable
fun ActionButton(icon: ImageVector, label: String) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF8B4513), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF8B4513))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ClientDetailPreview() {
    ClientDetail()
}
