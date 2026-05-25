package com.example.ordermanagementcake.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Orange80,
    onPrimary = Color(0xFF4E1D00),
    primaryContainer = Color(0xFF702B00),
    onPrimaryContainer = Color(0xFFFFDBCF),
    secondary = OrangeGrey80,
    onSecondary = Color(0xFF442B23),
    secondaryContainer = Color(0xFF5D4138),
    onSecondaryContainer = Color(0xFFFFDBCF),
    tertiary = Orange80,
    onTertiary = Color(0xFF4E1D00),
    background = Color(0xFF1F1B19),
    surface = Color(0xFF1F1B19),
    onBackground = Color(0xFFEAE0DC),
    onSurface = Color(0xFFEAE0DC),
    surfaceVariant = Color(0xFF52443D),
    onSurfaceVariant = Color(0xFFD7C2B9),
    outline = Color(0xFFA08D84)
)

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEDE6),
    onPrimaryContainer = Color(0xFF3B0900),
    secondary = OrangeSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDBCF),
    onSecondaryContainer = Color(0xFF350B00),
    tertiary = OrangeSecondary,
    onTertiary = Color.White,
    background = Color(0xFFFFFBFF),
    surface = Color(0xFFFFFBFF),
    onBackground = Color(0xFF201A18),
    onSurface = Color(0xFF201A18),
    surfaceVariant = Color(0xFFF3F3F3),
    onSurfaceVariant = Color(0xFF52443D),
    outline = Color(0xFF85736B)
)

@Composable
fun OrderManagementCakeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
