package com.example.ordermanagementcake.ui.forms.cakes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.data.draft.CakeDraft
import com.example.ordermanagementcake.data.draft.TierDraft
import com.example.ordermanagementcake.ui.forms.tier.NewTierForm
import com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme
import com.example.ordermanagementcake.ui.theme.extendedColors

// Main composable function for the Customize Cake screen
@Composable
fun NewCakeForm(
    onSaveCake: (CakeDraft) -> Unit = {},
    onBack: () -> Unit = {},
    clientName: String = "Sophie Chen"
) {
    var cakeTitle by remember { mutableStateOf("") }
    var cakeNotes by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) }
    var tiers by remember { mutableStateOf(emptyList<TierDraft>()) }
    var showTierForm by remember { mutableStateOf(false) }

    val extendedColors = MaterialTheme.extendedColors
    
    val estimatedTotal = tiers.sumOf { it.price }

    if (showTierForm) {
        NewTierForm(
            onDismiss = { showTierForm = false },
            onSave = { newTiers ->
                tiers = newTiers
                showTierForm = false
            }
        )
    }

    // Main UI structure without Scaffold (to avoid double padding in NavGraph)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Header tag for Order Configuration
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "KONFIGURASAUN PEDIDU",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    letterSpacing = 0.5.sp
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Screen Title: Artisanal Choice
            Text(
                text = "Escolha Artesanál",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            // Subtitle showing the client name
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = "Personaliza ba ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = clientName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Section for Cake Title
            Text(
                text = "TITULU CAKE",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = cakeTitle,
                onValueChange = { cakeTitle = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { 
                    Text("Ez: Bolo Kazamentu Ezequiel", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)) 
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedContainerColor = extendedColors.surfaceContainerLow,
                    unfocusedContainerColor = extendedColors.surfaceContainerLowest,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Section for adding a Reference Image
            SectionCard(title = "IMAJEN REFERÉNSIA") {
                DashedAddBox(
                    icon = Icons.Default.AddPhotoAlternate,
                    title = "AUMENTA IMAJEN REFERÉNSIA",
                    description = "Muda foto husi dezeñu bolo ne'ebé Ita gosta",
                    onClick = { /* Handle image selection */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section for adding Tier Specifications
            SectionCard(title = "ESPESIFIKASAUN NIVÉL") {
                if (tiers.isEmpty()) {
                    DashedAddBox(
                        icon = Icons.Default.Add,
                        title = "AUMENTA NIVÉL",
                        description = null,
                        onClick = { showTierForm = true }
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        tiers.forEach { tier ->
                            TierItem(tier)
                        }
                        Button(
                            onClick = { showTierForm = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            Text("Modifika Nívél", color = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Section for Cake Notes
            Text(
                text = "NOTA CAKE",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = cakeNotes,
                onValueChange = { cakeNotes = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Adisiona nota ruma kona-ba dezeñu ka sabór bolo nian...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedContainerColor = extendedColors.surfaceContainerLow,
                    unfocusedContainerColor = extendedColors.surfaceContainerLowest,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
                shape = RoundedCornerShape(16.dp),
                minLines = 4,
                maxLines = 6
            )
            
            Spacer(modifier = Modifier.height(40.dp))
        }
        
        // Bottom bar containing the estimated total and save button
        BottomSummaryBar(
            estimatedTotal = "%.2f".format(estimatedTotal),
            onEditTotalClick = {
                // Total is now calculated from tiers
            },
            onSaveCake = {
                onSaveCake(CakeDraft(cakeTitle = cakeTitle, cakeNotes = cakeNotes, imageUri = imageUri, tiers = tiers))
            }
        )
    }
}

@Composable
fun TierItem(tier: TierDraft) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Visual Color Indicator
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(tier.color)
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Nívél ${tier.level}: ${tier.shape}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Tamanho: ${tier.size}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "$${"%.2f".format(tier.price)}",
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
        }
    }
}

// Reusable card container for different form sections
@Composable
fun SectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.extendedColors.surfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Section Title
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
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
    onClick: () -> Unit
) {
    val outlineColor = MaterialTheme.colorScheme.outlineVariant
    
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
                    color = outlineColor,
                    style = stroke,
                    cornerRadius = CornerRadius(24.dp.toPx())
                )
            }
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(vertical = 32.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Icon container with circular background
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Label for the add action
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            // Optional description text below the title
            if (description != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Bottom bar containing the price summary and save button
@Composable
fun BottomSummaryBar(
    estimatedTotal: String,
    onEditTotalClick: () -> Unit,
    onSaveCake: () -> Unit
) {
    Surface(
        color = MaterialTheme.extendedColors.surfaceContainerLowest,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 24.dp,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Price estimation display
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onEditTotalClick() }
                    .padding(4.dp)
            ) {
                Text(
                    text = "ESTIMASAUN TOTÁL",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "$$estimatedTotal",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Muda Estimasaun",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            
            // Primary action button to save changes
            Button(
                onClick = onSaveCake,
                modifier = Modifier
                    .height(60.dp)
                    .width(180.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoFixHigh,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rai Mudansa",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

// Preview function for Android Studio IDE
@Preview(showBackground = true)
@Composable
fun CustomizeCakeFormPreview() {
    OrderManagementCakeTheme {
        NewCakeForm()
    }
}
