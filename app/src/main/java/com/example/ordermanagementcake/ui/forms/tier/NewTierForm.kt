package com.example.ordermanagementcake.ui.forms.tier

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme
import com.example.ordermanagementcake.ui.theme.extendedColors

/**
 * Data class hodi rai informasaun nívél ida-idak nian.
 */
data class TierData(
    val shape: String = "Round",
    val color: Color = Color.White,
    val price: Double = 120.0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTierForm(
    onDismiss: () -> Unit = {},
    onSave: (Map<Int, TierData>) -> Unit = { _ -> }
) {
    val extendedColors = MaterialTheme.extendedColors
    val surfaceColor = extendedColors.surfaceContainerLowest

    // Estadu ba nívél ne'ebé sedang hili (Active tier level selection)
    var activeTierLevel by remember { mutableIntStateOf(1) }
    
    // Inisializa nívél 1 to'o 12 (1..12)
    val tiersState = remember { 
        val initialMap = mutableStateMapOf<Int, TierData>()
        for (i in 1..12) {
            initialMap[i] = TierData(shape = "Round", color = Color.White, price = 120.0)
        }
        initialMap
    }

    val currentTierData = tiersState[activeTierLevel] ?: TierData()

    // Estadu ba Dropdown Forma
    var shapeExpanded by remember { mutableStateOf(false) }
    val shapes = listOf("Round", "Square", "Heart")

    // Estadu ba Color Picker Dialog
    var showColorPicker by remember { mutableStateOf(false) }

    // Kór sira ne'ebé hatudu ona (Quick color swatches) - Hamoos balun hodi husik fatin ba kustom
    val quickFrostingColors = listOf(
        Color.White, 
        Color(0xFFEE82D7),
        Color(0xFF39EA2C),
        Color(0xFF4C77F6)
    )

    // Hatudu Advanced Color Picker Dialog
    if (showColorPicker) {
        ModernColorPickerDialog(
            initialColor = currentTierData.color,
            onColorSelected = { color ->
                tiersState[activeTierLevel] = currentTierData.copy(color = color)
                showColorPicker = false
            },
            onDismiss = { showColorPicker = false }
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        containerColor = surfaceColor,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding() 
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState()) 
        ) {
            // Header: Títulu no Taka
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Espesifikasaun Nívél",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    onClick = onDismiss,
                    shape = CircleShape,
                    color = extendedColors.surfaceContainerHigh
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Taka",
                        modifier = Modifier.padding(8.dp).size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // SELESAUN NÍVÉL: 1 to'o 12
            Text(
                text = "SELESIONA NÍVÉL (1 - 12)",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tiersState.keys.toList().sorted()) { level ->
                    TierSelectionButton(
                        tierNumber = level,
                        isSelected = activeTierLevel == level,
                        onClick = { activeTierLevel = level },
                        modifier = Modifier.width(65.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // FORMA no PRESU
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Dropdown ba Forma
                Column(modifier = Modifier.weight(1.2f)) {
                    Text(
                        text = "FORMA", 
                        style = MaterialTheme.typography.labelMedium, 
                        fontWeight = FontWeight.Bold, 
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    ExposedDropdownMenuBox(
                        expanded = shapeExpanded,
                        onExpandedChange = { shapeExpanded = !shapeExpanded }
                    ) {
                        Surface(
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = extendedColors.surfaceContainerLow
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = currentTierData.shape, 
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold, 
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        
                        ExposedDropdownMenu(
                            expanded = shapeExpanded,
                            onDismissRequest = { shapeExpanded = false },
                            containerColor = extendedColors.surfaceContainer
                        ) {
                            shapes.forEach { shape ->
                                DropdownMenuItem(
                                    text = { Text(shape, color = MaterialTheme.colorScheme.onSurface) },
                                    onClick = {
                                        tiersState[activeTierLevel] = currentTierData.copy(shape = shape)
                                        shapeExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Presu Nívél
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "PRESU NÍVÉL", 
                        style = MaterialTheme.typography.labelMedium, 
                        fontWeight = FontWeight.Bold, 
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = extendedColors.surfaceContainerLow
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = {
                                    val newPrice = (currentTierData.price - 1.0).coerceAtLeast(0.0)
                                    tiersState[activeTierLevel] = currentTierData.copy(price = newPrice)
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Menos", tint = MaterialTheme.colorScheme.primary)
                            }
                            
                            Text(
                                text = "$${"%.0f".format(currentTierData.price)}", 
                                fontWeight = FontWeight.Bold, 
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 15.sp
                            )

                            IconButton(
                                onClick = {
                                    val newPrice = currentTierData.price + 1.0
                                    tiersState[activeTierLevel] = currentTierData.copy(price = newPrice)
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Aumenta", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // KÓR FROSTING
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "KÓR FROSTING", 
                    style = MaterialTheme.typography.labelMedium, 
                    fontWeight = FontWeight.Bold, 
                    color = MaterialTheme.colorScheme.primary
                )
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer, 
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "LEVEL $activeTierLevel",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = extendedColors.surfaceContainerLow
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Hatudu kór sira ne'ebé hili lalais
                    quickFrostingColors.forEach { color ->
                        ColorSwatchCircle(
                            color = color,
                            isSelected = currentTierData.color == color,
                            onClick = {
                                tiersState[activeTierLevel] = currentTierData.copy(color = color)
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Custom color indicator and picker trigger
                    // Se kór ne'ebé hili la'ós ida husi quick list, hatudu kór kustom ne'e iha sirkulu ikus
                    val isCustomColor = currentTierData.color !in quickFrostingColors
                    
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(if (isCustomColor) extendedColors.surfaceContainerHigh else Color.Transparent)
                            .border(
                                width = if (isCustomColor) 2.dp else 0.dp,
                                color = if (isCustomColor) MaterialTheme.colorScheme.primary else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { showColorPicker = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(if (isCustomColor) currentTierData.color else extendedColors.surfaceContainerHighest)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ColorLens,
                                contentDescription = "Color Picker",
                                tint = if (isCustomColor) {
                                    if (currentTierData.color.toArgb() == Color.White.toArgb()) Color.Gray else Color.White
                                } else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Markup adisionál
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Aumentu Adisionál", 
                    style = MaterialTheme.typography.titleMedium, 
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "+ $15.00", 
                    style = MaterialTheme.typography.titleMedium, 
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Botão Save
            Button(
                onClick = { onSave(tiersState.toMap()) },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Atualiza Espesifikasaun Nívél", 
                    style = MaterialTheme.typography.titleMedium, 
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Modern Color Picker Dialog with Spectrum Selection and HEX input.
 */
@Composable
fun ModernColorPickerDialog(
    initialColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    val hsv = remember(initialColor) {
        val hsvArr = FloatArray(3)
        android.graphics.Color.colorToHSV(initialColor.toArgb(), hsvArr)
        hsvArr
    }
    
    val hsvState = remember { mutableStateListOf(hsv[0], hsv[1], hsv[2]) }
    val currentColor = Color.hsv(hsvState[0], hsvState[1], hsvState[2])
    
    var hexInput by remember { 
        val argb = currentColor.toArgb()
        mutableStateOf(String.format("%06X", 0xFFFFFF and argb)) 
    }
    
    LaunchedEffect(hsvState[0], hsvState[1], hsvState[2]) {
        val argb = Color.hsv(hsvState[0], hsvState[1], hsvState[2]).toArgb()
        hexInput = String.format("%06X", 0xFFFFFF and argb)
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.extendedColors.surfaceContainerLowest)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hili Kór", 
                    fontWeight = FontWeight.Bold, 
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                SaturationValueSelector(
                    hue = hsvState[0],
                    saturation = hsvState[1],
                    value = hsvState[2],
                    onSaturationValueChange = { s, v ->
                        hsvState[1] = s
                        hsvState[2] = v
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                HueSlider(
                    hue = hsvState[0],
                    onHueChange = { h -> hsvState[0] = h }
                )

                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(currentColor)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                    )

                    OutlinedTextField(
                        value = hexInput,
                        onValueChange = { input ->
                            if (input.length <= 6) {
                                hexInput = input.uppercase()
                                try {
                                    if (input.length == 6) {
                                        val colorInt = android.graphics.Color.parseColor("#$hexInput")
                                        val newHsv = FloatArray(3)
                                        android.graphics.Color.colorToHSV(colorInt, newHsv)
                                        hsvState[0] = newHsv[0]
                                        hsvState[1] = newHsv[1]
                                        hsvState[2] = newHsv[2]
                                    }
                                } catch (e: Exception) { }
                            }
                        },
                        label = { Text("HEX") },
                        prefix = { Text("# ") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                            focusedContainerColor = MaterialTheme.extendedColors.surfaceContainerLow,
                            unfocusedContainerColor = MaterialTheme.extendedColors.surfaceContainerLowest
                        )
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("KANSELA", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onColorSelected(currentColor) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("SELESIONA", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun SaturationValueSelector(
    hue: Float,
    saturation: Float,
    value: Float,
    onSaturationValueChange: (Float, Float) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val s = (change.position.x / size.width).coerceIn(0f, 1f)
                    val v = 1f - (change.position.y / size.height).coerceIn(0f, 1f)
                    onSaturationValueChange(s, v)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(color = Color.hsv(hue, 1f, 1f))
            drawRect(brush = Brush.horizontalGradient(colors = listOf(Color.White, Color.Transparent)))
            drawRect(brush = Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black)))

            val selectorPos = Offset(saturation * size.width, (1f - value) * size.height)
            drawCircle(
                color = if (value > 0.5f) Color.White else Color.Black,
                radius = 8.dp.toPx(),
                center = selectorPos,
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}

@Composable
fun HueSlider(
    hue: Float,
    onHueChange: (Float) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .clip(CircleShape)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val h = (change.position.x / size.width).coerceIn(0f, 1f) * 360f
                    onHueChange(h)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val hueColors = List(361) { Color.hsv(it.toFloat(), 1f, 1f) }
            drawRect(brush = Brush.horizontalGradient(colors = hueColors))
            val x = (hue / 360f) * size.width
            drawCircle(
                color = Color.White,
                radius = 10.dp.toPx(),
                center = Offset(x, size.height / 2),
                style = Stroke(width = 3.dp.toPx())
            )
        }
    }
}

@Composable
fun TierSelectionButton(
    tierNumber: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(56.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = tierNumber.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ColorSwatchCircle(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (isSelected) MaterialTheme.extendedColors.surfaceContainerHigh else Color.Transparent)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(color)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TierFormPreview() {
    OrderManagementCakeTheme {
        NewTierForm()
    }
}
