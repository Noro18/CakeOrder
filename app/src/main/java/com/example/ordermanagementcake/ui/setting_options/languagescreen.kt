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
 * A Dialog wrapper to display the Language selection popup.
 */
@Composable
fun LanguageDialog(
    onDismissRequest: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        LanguageDialogContent(
            onDismiss = onDismissRequest,
            onSelect = onLanguageSelected
        )
    }
}

/**
 * The internal content of the Language selection dialog.
 */
@Composable
fun LanguageDialogContent(
    onDismiss: () -> Unit = {},
    onSelect: (String) -> Unit = {}
) {
    // State to keep track of the currently selected language
    var selectedOption by remember { mutableStateOf("Tetum") }
    val options = listOf("Tetum", "Indonesia", "Portugues", "English")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
        ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Dialog Title
            Text(
                text = "Lian",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // List of Language Options
            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (option == selectedOption),
                            onClick = { 
                                selectedOption = option
                                onSelect(option) // Trigger callback on selection
                            },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = null, // Interaction is handled by Row's selectable modifier
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFFE56141), // Light blue color when selected
                            unselectedColor = Color(0xFF757575) // Gray color when unselected
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

            // Close button at the bottom-right
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                OutlinedButton(
                    onClick = onDismiss,
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
fun LanguageDialogPreview() {
    LanguageDialogContent()
}
