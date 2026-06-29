package com.example.ordermanagementcake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.ordermanagementcake.ui.navigation.Routes
import com.example.ordermanagementcake.ui.theme.extendedColors

data class DrawerItem(
    var label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun AppDrawer(
    selectedItem: Int = 0,
    onNavigate: (String) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onClose: () -> Unit
) {
    val extendedColors = MaterialTheme.extendedColors
    val items = listOf(
        DrawerItem("Dashboard",  Icons.Default.Dashboard)        { onNavigate(Routes.DASHBOARD); onClose() },
        DrawerItem("Pedidu",     Icons.Default.AddShoppingCart)  { onNavigate(Routes.ORDERS); onClose() },
        DrawerItem("Kliente",    Icons.Default.People)           { onNavigate(Routes.CLIENTS); onClose() },
        DrawerItem("Orario",  Icons.Default.CalendarMonth)    { onNavigate(Routes.SCHEDULES); onClose() },
    )

    val bottomItems = listOf(
        DrawerItem("Konfigurasaun",   Icons.Default.Settings)         { onSettingsClick(); onClose() },
        DrawerItem("Kona-ba",      Icons.Default.Info)             { onAboutClick(); onClose() },
    )

    ModalDrawerSheet(
        drawerContainerColor = extendedColors.surfaceContainer,
        drawerContentColor   = MaterialTheme.colorScheme.onSurface,
    ) {

        // ── Header ────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(extendedColors.surfaceContainer)
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            Column {
                // Avatar circle
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Store,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    text = "Key's Cake Bakery",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.3.sp
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = "Jestaun Pedidu",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // ── Section label ──────────────────────────────────────
        Text(
            text = "MENU",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 4.dp),
            letterSpacing = 1.5.sp
        )

        Spacer(Modifier.height(4.dp))

        // ── Navigation items ───────────────────────────────────
        items.forEachIndexed { index, item ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontWeight = FontWeight.Normal
                    )
                },
                selected = false,
                onClick = item.onClick,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedIconColor    = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor    = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
        }

        Spacer(Modifier.weight(1f))

        // ── Divider + bottom section label ────────────────────
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "JERAL",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 4.dp),
            letterSpacing = 1.5.sp
        )

        Spacer(Modifier.height(4.dp))

        // ── Bottom items ───────────────────────────────────────
        bottomItems.forEach { item ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontWeight = FontWeight.Normal
                    )
                },
                selected = false,
                onClick = item.onClick,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
        }

        // ── Footer version tag ─────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                tonalElevation = 0.dp
            ) {
                Text(
                    text = "v1.0.0",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}