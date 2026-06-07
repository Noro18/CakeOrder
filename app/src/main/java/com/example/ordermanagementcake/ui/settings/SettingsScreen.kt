package com.example.ordermanagementcake.ui.settings

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ordermanagementcake.ui.components.AppTopBarMuted
import com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme

data class SettingsItem(
    val title: String,
    val icon: ImageVector,
    val trailingContent: (@Composable () -> Unit)? = null
)

@Composable
fun SettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(false) }

    val preferenceItems = listOf(
        SettingsItem("Theme Mode", Icons.Default.Palette),
        SettingsItem("Language", Icons.Default.Language)
    )

    val supportItems = listOf(
        SettingsItem("Backup & Restore", Icons.Default.Backup),
        SettingsItem("Help & Support", Icons.Default.Help),
        SettingsItem("Privacy Policy", Icons.Default.PrivacyTip)
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
                                headlineContent = { Text(item.title) },
                                leadingContent = {
                                    Icon(item.icon, contentDescription = null)
                                },
                                trailingContent = {
                                    Icon(Icons.Default.ChevronRight, contentDescription = null)
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
                                headlineContent = { Text(item.title) },
                                leadingContent = {
                                    Icon(item.icon, contentDescription = null)
                                },
                                trailingContent = {
                                    Icon(Icons.Default.ChevronRight, contentDescription = null)
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

