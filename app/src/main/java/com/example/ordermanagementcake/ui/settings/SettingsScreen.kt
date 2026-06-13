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

data class SettingsItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit = {}
)

@Composable
fun SettingsScreen(onPriceTableClick: () -> Unit = {}) {
    val context = LocalContext.current
    var notificationsEnabled by remember { mutableStateOf(false) }

    val preferenceItems = listOf(
        SettingsItem("Theme Mode", Icons.Default.Palette) { 
            Toast.makeText(context, "Theme Mode clicked", Toast.LENGTH_SHORT).show() 
        },
        SettingsItem("Language", Icons.Default.Language) { 
            Toast.makeText(context, "Language clicked", Toast.LENGTH_SHORT).show() 
        },
        SettingsItem("Price Table", Icons.Default.AttachMoney) {
            onPriceTableClick()
        }
    )

    val supportItems = listOf(
        SettingsItem("Backup & Restore", Icons.Default.Backup) { 
            Toast.makeText(context, "Backup clicked", Toast.LENGTH_SHORT).show() 
        },
        SettingsItem("Help & Support", Icons.Default.Help) { 
            Toast.makeText(context, "Help clicked", Toast.LENGTH_SHORT).show() 
        },
        SettingsItem("Privacy Policy", Icons.Default.PrivacyTip) { 
            Toast.makeText(context, "Privacy Policy clicked", Toast.LENGTH_SHORT).show() 
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
