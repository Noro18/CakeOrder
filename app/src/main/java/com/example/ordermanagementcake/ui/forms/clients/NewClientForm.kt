package com.example.ordermanagementcake.ui.forms.clients

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ordermanagementcake.R

/**
 * Modern Popup for adding a new client.
 * Uses a Dialog wrapper for correct popup behavior.
 */
@Composable
fun NewClientForm(
    onDismiss: () -> Unit = {},
    onSave: (String, String, String) -> Unit = { _, _, _ -> },

) {
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }

    // Use Dialog for proper popup behavior
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false // Allow us to handle keyboard padding manually
        )
    ) {
        // Main container that handles keyboard and navigation bar padding
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding() // Pushes content up when keyboard appears
                .navigationBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.92f) // Takes 92% of screen width
                    .wrapContentHeight()
                    .padding(vertical = 24.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()) // Enables scrolling when fields are covered
                ) {
                    // Header with Image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.raspberrylayercake),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Gradient overlay to make text readable
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surface),
                                        startY = 120f
                                    )
                                )
                        )

                        // Title section
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(24.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Kliente Foun",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Adisiona ho lais dadus foun.",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }

                            // Decorative Icon
                            Surface(
                                modifier = Modifier.size(44.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFFFEDE6)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = null,
                                    tint = Color(0xFFF37B21),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }

                    // Input fields
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 24.dp)
                    ) {
                        InputFieldLabel(text = "NARAN KOMPLETU")
                        CustomFormTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            placeholder = "ez. Sarah Miller"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        InputFieldLabel(text = "NÚMERU TELEFONE")
                        CustomFormTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            placeholder = "+670 7000 0000"
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        InputFieldLabel(text = "HElA FATIN")
                        CustomFormTextField(
                            value = deliveryAddress,
                            onValueChange = { deliveryAddress = it },
                            placeholder = "Rua, Aldeia, Suku"
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        // Action Button
                        Button(
                            onClick = { onSave(fullName, phoneNumber, deliveryAddress) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE36D25))
                        ) {
                            Text(
                                text = "Rai & Kontinua",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Cancel link
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 8.dp)
                        ) {
                            Text(
                                text = "Kansela",
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputFieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF8C280E),
        letterSpacing = 0.5.sp,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomFormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = placeholder, fontSize = 14.sp) },
        shape = RoundedCornerShape(14.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF3F3F3),
            unfocusedContainerColor = Color(0xFFF3F3F3),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFFE36D25)
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun NewClientFormPreview() {
    NewClientForm()
}
