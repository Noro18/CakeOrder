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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.ui.forms.clients.NewClientForm

@Composable
fun ClientDetail(
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    var showEditForm by remember { mutableStateOf(false) }
    
    // Form States (Mock data for UI redesign)
    var name by remember { mutableStateOf("Eleanor Fitzgerald") }
    var phone by remember { mutableStateOf("+1 (555) 234-8890") }
    var address by remember { mutableStateOf("72 Oakwood Crescent, Maplewood Heights, NY 10012") }
    var notes by remember { mutableStateOf("Kliente ne'e gosta bolu strawberry ho dekorasaun minimalista.") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // THE ONE CARD: Consolidated Client Information
        ClientInfoCard(
            name = name,
            id = "#BC-8821",
            phone = phone,
            address = address,
            notes = notes,
            onEditClick = { showEditForm = true }
        )

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
                color = Color(0xFFC23C12).copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Totál 3",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC23C12)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OrderItem(
            title = "Bolu Selebrasaun Triple Berry",
            date = "OUT 14",
            time = "Foti iha tuku 11:30 AM",
            price = "$85.00",
            status = "Kompletu",
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
            status = "Kompletu",
            statusColor = Color(0xFF9E9E9E)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom Actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionButton(
                icon = Icons.Default.Add,
                label = "Enkomenda Foun",
                modifier = Modifier.weight(1f),
                containerColor = Color(0xFFC23C12),
                contentColor = Color.White
            )
            ActionButton(
                icon = Icons.Default.Email,
                label = "Kontaktu",
                modifier = Modifier.weight(1f),
                containerColor = Color.White,
                contentColor = Color(0xFFC23C12)
            )
        }
    }

    // Reuse NewClientForm for Editing
    if (showEditForm) {
        NewClientForm(
            title = "Edita Kliente",
            initialName = name,
            initialPhone = phone,
            initialAddress = address,
            initialNotes = notes,
            onDismiss = { showEditForm = false },
            onSave = { n, p, a, nt ->
                name = n
                phone = p
                address = a
                notes = nt
                showEditForm = false
            }
        )
    }
}

@Composable
fun ClientInfoCard(
    name: String,
    id: String,
    phone: String,
    address: String,
    notes: String,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Profile Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(64.dp),
                    shape = CircleShape,
                    color = Color(0xFFC23C12).copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = name.take(1).uppercase(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFC23C12)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Text(
                        text = "ID KLIENTE: $id",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(24.dp))

            // Info Rows
            InfoDetailRow(icon = Icons.Default.Phone, label = "NÚMERU TELEFONE", value = phone)
            Spacer(modifier = Modifier.height(20.dp))
            InfoDetailRow(icon = Icons.Default.LocationOn, label = "HELA FATIN", value = address)
            Spacer(modifier = Modifier.height(20.dp))
            InfoDetailRow(icon = Icons.Default.Description, label = "NOTA & PREFERÉNSIA", value = notes)

            Spacer(modifier = Modifier.height(16.dp))

            // Edit Button at the bottom of the card
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                FilledIconButton(
                    onClick = onEditClick,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(0xFFC23C12)
                    ),
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edita",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun InfoDetailRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.Top) {
        Surface(
            modifier = Modifier.size(36.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFF8F8F8)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xFFC23C12)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                letterSpacing = 0.5.sp
            )
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                lineHeight = 20.sp
            )
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
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF8F8F8))
            ) {
                Icon(
                    Icons.Default.Cake,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center).size(24.dp),
                    tint = Color(0xFFC23C12).copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = price,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = Color(0xFFC23C12)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "$date • $time", fontSize = 12.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = status.uppercase(),
                        color = statusColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color
) {
    Button(
        onClick = { },
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ClientDetailPreview() {
    ClientDetail()
}
