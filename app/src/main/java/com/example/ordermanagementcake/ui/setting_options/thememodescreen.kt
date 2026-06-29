package com.example.ordermanagementcake.ui.setting_options

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * This is a Dialog wrapper so that the UI can appear as a popup.
 */
@Composable
fun ThemeModeDialog(
    currentTheme: String,
    onDismissRequest: () -> Unit,
    onThemeSelected: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        ThemeModeDialogContent(
            initialTheme = currentTheme,
            onDismiss = onDismissRequest,
            onSelect = onThemeSelected
        )
    }
}

@Composable
fun ThemeModeDialogContent(
    initialTheme: String = "Sistems default",
    onDismiss: () -> Unit = {},
    onSelect: (String) -> Unit = {}
) {
    var selectedOption by remember { mutableStateOf(initialTheme) }
    val options = listOf("Naroman", "Nakukun", "Sistems default")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Tema Mode",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (option == selectedOption),
                            onClick = { 
                                selectedOption = option
                                onSelect(option) // Call the callback when selected
                            },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFFEA763F),
                            unselectedColor = Color(0xFF757575)
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                OutlinedButton(
                    onClick = onDismiss, // Use the callback to close
                    border = BorderStroke(1.dp, Color(0xFF757575)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Taka")
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ThemeModeDialogPreview() {
    ThemeModeDialogContent()
}
