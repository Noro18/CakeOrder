package com.example.ordermanagementcake.ui.customizecakeform

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Main composable function for the Customize Cake screen
@Composable
fun customizeCakeForm() {
    val mainOrange = Color(0xFFF37B21)
    val textBrown = Color(0xFF8C280E)
    val bgColor = Color(0xFFF8F8F8)

    // Main UI structure using Scaffold
    Scaffold(
        bottomBar = {
            // Bottom bar containing the estimated total and save button
            BottomSummaryBar(mainOrange)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(bgColor)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Header tag for Order Configuration
            Surface(
                color = Color(0xFFFFEDE6),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "KONFIGURASAUN PEDIDU",
                    color = Color(0xFFD48259),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    letterSpacing = 0.5.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Screen Title: Artisanal Choice
            Text(
                text = "Escolha Artesanál",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A4A4A)
            )
            
            // Subtitle showing the client name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Personaliza ba ",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Sophie Chen",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD48259)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Row containing client contact information chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoChip(Icons.Default.Phone, "+1 (555) 012-3456")
                InfoChip(Icons.Default.LocationOn, "Brooklyn, NY")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Section for adding a Reference Image
            SectionCard(title = "IMAJEN REFERÉNSIA") {
                DashedAddBox(
                    icon = Icons.Default.AddPhotoAlternate,
                    title = "AUMENTA IMAJEN REFERÉNSIA",
                    description = "Muda foto husi dezeñu bolo ne'ebé Ita gosta",
                    mainColor = textBrown
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section for adding Tier Specifications
            SectionCard(title = "ESPESIFIKASAUN NIVÉL") {
                DashedAddBox(
                    icon = Icons.Default.Add,
                    title = "AUMENTA NIVÉL",
                    description = null,
                    mainColor = textBrown
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// Reusable component for displaying an information chip with an icon
@Composable
fun InfoChip(icon: ImageVector, text: String) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 0.5.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFD48259),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontSize = 13.sp, color = Color.DarkGray)
        }
    }
}

// Reusable card container for different form sections
@Composable
fun SectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Section Title
            Text(
                text = title,
                color = Color(0xFF8C280E),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Slot for section-specific content
            content()
        }
    }
}

// Reusable box with a dashed border for "Add" actions
@Composable
fun DashedAddBox(
    icon: ImageVector,
    title: String,
    description: String?,
    mainColor: Color
) {
    // Define the style for the dashed border
    val stroke = Stroke(
        width = 4f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                // Draw the dashed rounded rectangle border
                drawRoundRect(
                    color = Color.LightGray.copy(alpha = 0.8f),
                    style = stroke,
                    cornerRadius = CornerRadius(16.dp.toPx())
                )
            }
            .clickable { /* Action when clicking the dashed box */ }
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Icon container with circular background
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFEDE6)),
                contentAlignment = Alignment.Center
            ) {
                // Clickable icon button
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = mainColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Label for the add action
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
            // Optional description text below the title
            if (description != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// Bottom bar containing the price summary and save button
@Composable
fun BottomSummaryBar(orangeColor: Color) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 16.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Price estimation display
            Column {
                Text(
                    text = "ESTIMASAUN TOTÁL",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "$85.00",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF333333)
                )
            }
            
            // Primary action button to save changes
            Button(
                onClick = { },
                modifier = Modifier
                    .height(56.dp)
                    .width(180.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoFixHigh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rai Mudansa sira",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

// Preview function for Android Studio IDE
@Preview(showBackground = true)
@Composable
fun customizeCakeFormPreview() {
    customizeCakeForm()
}
