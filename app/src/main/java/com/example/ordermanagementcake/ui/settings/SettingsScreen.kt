package com.example.ordermanagementcake.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ordermanagementcake.ui.components.AppTopBarNewOrder
import com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme

@Composable
fun SettingsScreen() {
    val settingsItems = listOf(
        "Theme Mode",
        "Language",
        "Backup & Restore",
        "Help & Support",
        "Privacy Policy"
    )

    var notificationsEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "General Settings",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Enable Notifications",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Checkbox(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn {
            items(settingsItems) { item ->
                ListItem(
                    headlineContent = { Text(item) },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    OrderManagementCakeTheme {
        Scaffold(
            topBar = {
                AppTopBarNewOrder(
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
