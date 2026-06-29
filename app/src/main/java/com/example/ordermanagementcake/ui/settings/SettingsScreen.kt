package com.example.ordermanagementcake.ui.settings

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ordermanagementcake.ui.components.AppTopBarMuted
import com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme
import com.example.ordermanagementcake.ui.setting_options.ThemeModeDialog
import com.example.ordermanagementcake.ui.setting_options.LanguageDialog
import com.example.ordermanagementcake.ui.setting_options.BackupRestoreSheet
import com.example.ordermanagementcake.ui.setting_options.PrivacyPolicySheet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet

data class SettingsItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentTheme: String = "Sistems default",
    onThemeChange: (String) -> Unit = {},
    onPriceTableClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var notificationsEnabled by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showBackupSheet by remember { mutableStateOf(false) }
    var showPrivacySheet by remember { mutableStateOf(false) }

    if (showThemeDialog) {
        ThemeModeDialog(
            currentTheme = currentTheme,
            onDismissRequest = { showThemeDialog = false },
            onThemeSelected = { newTheme ->
                onThemeChange(newTheme)
                showThemeDialog = false
            }
        )
    }

    if (showLanguageDialog) {
        LanguageDialog(
            onDismissRequest = { showLanguageDialog = false },
            onLanguageSelected = { selectedLanguage ->
                Toast.makeText(context, "Language changed to: $selectedLanguage", Toast.LENGTH_SHORT).show()
                showLanguageDialog = false
            }
        )
    }

    if (showBackupSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBackupSheet = false },
            dragHandle = null
        ) {
            BackupRestoreSheet(
                onBackupClick = {
                    Toast.makeText(context, "Backup initiated", Toast.LENGTH_SHORT).show()
                    showBackupSheet = false
                },
                onRestoreClick = {
                    Toast.makeText(context, "Restore initiated", Toast.LENGTH_SHORT).show()
                    showBackupSheet = false
                }
            )
        }
    }

    if (showPrivacySheet) {
        ModalBottomSheet(
            onDismissRequest = { showPrivacySheet = false },
            dragHandle = null
        ) {
            PrivacyPolicySheet()
        }
    }

    val preferenceItems = listOf(
        SettingsItem("Theme Mode", Icons.Default.Palette) { 
            showThemeDialog = true
        },
        SettingsItem("Language", Icons.Default.Language) { 
            showLanguageDialog = true
        },
        SettingsItem("Price Table", Icons.Default.AttachMoney) {
            onPriceTableClick()
        }
    )

    val supportItems = listOf(
        SettingsItem("Backup & Restore", Icons.Default.Backup) { 
            showBackupSheet = true
        },
        SettingsItem("Help & Support", Icons.Default.Help) { 
            Toast.makeText(context, "Help clicked", Toast.LENGTH_SHORT).show() 
        },
        SettingsItem("Privacy Policy", Icons.Default.PrivacyTip) { 
            showPrivacySheet = true
        }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // ── General Section ───────────────────────────────────
        item {
            Column {
                Text(
                    text = "General",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ListItem(
                        modifier = Modifier.clickable { notificationsEnabled = !notificationsEnabled },
                        headlineContent = { Text("Enable Notifications") },
                        leadingContent = {
                            Icon(Icons.Default.Notifications, contentDescription = null)
                        },
                        trailingContent = {
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { notificationsEnabled = it }
                            )
                        },
                        colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
                    )
                }
            }
        }

        // ── Preferences Section ──────────────────────────────
        item {
            Column {
                Text(
                    text = "Preferences",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        preferenceItems.forEach { item ->
                            ListItem(
                                modifier = Modifier.clickable { item.onClick() },
                                headlineContent = { Text(item.title) },
                                leadingContent = {
                                    Icon(item.icon, contentDescription = null)
                                },
                                trailingContent = {
                                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                                },
                                colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
                            )
                        }
                    }
                }
            }
        }

        // ── Support Section ──────────────────────────────────
        item {
            Column {
                Text(
                    text = "System & Support",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        supportItems.forEach { item ->
                            ListItem(
                                modifier = Modifier.clickable { item.onClick() },
                                headlineContent = { Text(item.title) },
                                leadingContent = {
                                    Icon(item.icon, contentDescription = null)
                                },
                                trailingContent = {
                                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                                },
                                colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
                            )
                        }
                    }
                }
            }
        }
        
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    OrderManagementCakeTheme {
        Scaffold(
            topBar = {
                AppTopBarMuted(
                    title = "Settings",
                    onBackClick = {}
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                SettingsScreen()
            }
        }
    }
}
