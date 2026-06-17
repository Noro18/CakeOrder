package com.example.ordermanagementcake.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.ui.theme.extendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBarDelete(
    title: String = "Key's Cake Bakery",
    onBackClick: (() -> Unit)? = null,
    onMenuClick: (() -> Unit)? = null,
    onDeleteClick: () -> Unit = {}
) {
    val extendedColors = MaterialTheme.extendedColors
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else {
                IconButton(onClick = { onMenuClick?.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor             = extendedColors.sourceColor,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor     = MaterialTheme.colorScheme.onPrimary,
            titleContentColor          = MaterialTheme.colorScheme.onPrimary
        )
    )
}
/*

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopBarDeletePreview() {
    AppTopBarDelete()
}*/
