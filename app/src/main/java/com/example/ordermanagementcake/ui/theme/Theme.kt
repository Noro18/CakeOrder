package com.example.ordermanagementcake.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ══════════════════════════════════════════════════════════════════════════════
// RAW COLOR TOKENS
// ══════════════════════════════════════════════════════════════════════════════

object LightColors {
    val background                  = Color(0xFFFFF8F6)
    val blue                        = Color(0xFF4B5C92)
    val blueContainer               = Color(0xFFDBE1FF)
    val blueSource                  = Color(0xFF3F597B)
    val blueValue                   = Color(0xFF3F597B)
    val error                       = Color(0xFFBA1A1A)
    val errorContainer              = Color(0xFFFFDAD6)
    val inverseOnSurface            = Color(0xFFFFEDE8)
    val inversePrimary              = Color(0xFFFFB59C)
    val inverseSurface              = Color(0xFF392E2B)
    val onBackground                = Color(0xFF231917)
    val onBlue                      = Color(0xFFFFFFFF)
    val onBlueContainer             = Color(0xFF00174B)
    val onError                     = Color(0xFFFFFFFF)
    val onErrorContainer            = Color(0xFF410002)
    val onPrimary                   = Color(0xFFFFFFFF)
    val onPrimaryContainer          = Color(0xFF390C00)
    val onPrimaryFixed              = Color(0xFF390C00)
    val onPrimaryFixedVariant       = Color(0xFF723520)
    val onRed                       = Color(0xFFFFFFFF)
    val onRedContainer              = Color(0xFF3A0904)
    val onSecondary                 = Color(0xFFFFFFFF)
    val onSecondaryContainer        = Color(0xFF2C160E)
    val onSecondaryFixed            = Color(0xFF2C160E)
    val onSecondaryFixedVariant     = Color(0xFF5D4036)
    val onSuccess                   = Color(0xFFFFFFFF)
    val onSuccessContainer          = Color(0xFF112000)
    val onSurface                   = Color(0xFF231917)
    val onSurfaceVariant            = Color(0xFF53433F)
    val onTertiary                  = Color(0xFFFFFFFF)
    val onTertiaryContainer         = Color(0xFF221B00)
    val onTertiaryFixed             = Color(0xFF221B00)
    val onTertiaryFixedVariant      = Color(0xFF51461A)
    val onYellow                    = Color(0xFFFFFFFF)
    val onYellowContainer           = Color(0xFF231B00)
    val outline                     = Color(0xFF85736E)
    val outlineVariant              = Color(0xFFD8C2BB)
    val primary                     = Color(0xFF8F4C34)
    val primaryContainer            = Color(0xFFFFDBCF)
    val primaryFixed                = Color(0xFFFFDBCF)
    val primaryFixedDim             = Color(0xFFFFB59C)
    val red                         = Color(0xFF904B3F)
    val redContainer                = Color(0xFFFFDAD4)
    val redSource                   = Color(0xFF9A4141)
    val redValue                    = Color(0xFF9A4141)
    val scrim                       = Color(0xFF000000)
    val secondary                   = Color(0xFF77574C)
    val secondaryContainer          = Color(0xFFFFDBCF)
    val secondaryFixed              = Color(0xFFFFDBCF)
    val secondaryFixedDim           = Color(0xFFE7BDB0)
    val shadow                      = Color(0xFF000000)
    val sourceColor                 = Color(0xFFE04D0C)
    val success                     = Color(0xFF4D662A)
    val successContainer            = Color(0xFFCEEDA2)
    val successSource               = Color(0xFF358738)
    val successValue                = Color(0xFF358738)
    val surface                     = Color(0xFFFFF8F6)
    val surfaceBright               = Color(0xFFFFF8F6)
    val surfaceContainer            = Color(0xFFFCEAE5)
    val surfaceContainerHigh        = Color(0xFFF7E4DF)
    val surfaceContainerHighest     = Color(0xFFF1DFD9)
    val surfaceContainerLow         = Color(0xFFFFF1ED)
    val surfaceContainerLowest      = Color(0xFFFFFFFF)
    val surfaceDim                  = Color(0xFFE8D6D1)
    val surfaceTint                 = Color(0xFF8F4C34)
    val surfaceVariant              = Color(0xFFF5DED7)
    val tertiary                    = Color(0xFF6A5E2F)
    val tertiaryContainer           = Color(0xFFF3E2A7)
    val tertiaryFixed               = Color(0xFFF3E2A7)
    val tertiaryFixedDim            = Color(0xFFD6C68D)
    val yellow                      = Color(0xFF715C0C)
    val yellowContainer             = Color(0xFFFEE086)
    val yellowSource                = Color(0xFFC0BA17)
    val yellowValue                 = Color(0xFFC0BA17)
}

object DarkColors {
    val background                  = Color(0xFF1A110F)
    val blue                        = Color(0xFFB4C5FF)
    val blueContainer               = Color(0xFF334478)
    val blueSource                  = Color(0xFF3F597B)
    val blueValue                   = Color(0xFF3F597B)
    val error                       = Color(0xFFFFB4AB)
    val errorContainer              = Color(0xFF93000A)
    val inverseOnSurface            = Color(0xFF392E2B)
    val inversePrimary              = Color(0xFF8F4C34)
    val inverseSurface              = Color(0xFFF1DFD9)
    val onBackground                = Color(0xFFF1DFD9)
    val onBlue                      = Color(0xFF1B2D60)
    val onBlueContainer             = Color(0xFFDBE1FF)
    val onError                     = Color(0xFF690005)
    val onErrorContainer            = Color(0xFFFFDAD6)
    val onPrimary                   = Color(0xFF55200C)
    val onPrimaryContainer          = Color(0xFFFFDBCF)
    val onPrimaryFixed              = Color(0xFF390C00)
    val onPrimaryFixedVariant       = Color(0xFF723520)
    val onRed                       = Color(0xFF561E16)
    val onRedContainer              = Color(0xFFFFDAD4)
    val onSecondary                 = Color(0xFF442A21)
    val onSecondaryContainer        = Color(0xFFFFDBCF)
    val onSecondaryFixed            = Color(0xFF2C160E)
    val onSecondaryFixedVariant     = Color(0xFF5D4036)
    val onSuccess                   = Color(0xFF203600)
    val onSuccessContainer          = Color(0xFFCEEDA2)
    val onSurface                   = Color(0xFFF1DFD9)
    val onSurfaceVariant            = Color(0xFFD8C2BB)
    val onTertiary                  = Color(0xFF393005)
    val onTertiaryContainer         = Color(0xFFF3E2A7)
    val onTertiaryFixed             = Color(0xFF221B00)
    val onTertiaryFixedVariant      = Color(0xFF51461A)
    val onYellow                    = Color(0xFF3C2F00)
    val onYellowContainer           = Color(0xFFFEE086)
    val outline                     = Color(0xFFA08D87)
    val outlineVariant              = Color(0xFF53433F)
    val primary                     = Color(0xFFFFB59C)
    val primaryContainer            = Color(0xFF723520)
    val primaryFixed                = Color(0xFFFFDBCF)
    val primaryFixedDim             = Color(0xFFFFB59C)
    val red                         = Color(0xFFFFB4A7)
    val redContainer                = Color(0xFF73342A)
    val redSource                   = Color(0xFF9A4141)
    val redValue                    = Color(0xFF9A4141)
    val scrim                       = Color(0xFF000000)
    val secondary                   = Color(0xFFE7BDB0)
    val secondaryContainer          = Color(0xFF5D4036)
    val secondaryFixed              = Color(0xFFFFDBCF)
    val secondaryFixedDim           = Color(0xFFE7BDB0)
    val shadow                      = Color(0xFF000000)
    val sourceColor                 = Color(0xFFE04D0C)
    val success                     = Color(0xFFB3D088)
    val successContainer            = Color(0xFF364E14)
    val successSource               = Color(0xFF358738)
    val successValue                = Color(0xFF358738)
    val surface                     = Color(0xFF1A110F)
    val surfaceBright               = Color(0xFF423733)
    val surfaceContainer            = Color(0xFF271D1A)
    val surfaceContainerHigh        = Color(0xFF322825)
    val surfaceContainerHighest     = Color(0xFF3D322F)
    val surfaceContainerLow         = Color(0xFF231917)
    val surfaceContainerLowest      = Color(0xFF140C0A)
    val surfaceDim                  = Color(0xFF1A110F)
    val surfaceTint                 = Color(0xFFFFB59C)
    val surfaceVariant              = Color(0xFF53433F)
    val tertiary                    = Color(0xFFD6C68D)
    val tertiaryContainer           = Color(0xFF51461A)
    val tertiaryFixed               = Color(0xFFF3E2A7)
    val tertiaryFixedDim            = Color(0xFFD6C68D)
    val yellow                      = Color(0xFFE1C46D)
    val yellowContainer             = Color(0xFF564500)
    val yellowSource                = Color(0xFFC0BA17)
    val yellowValue                 = Color(0xFFC0BA17)
}

// ══════════════════════════════════════════════════════════════════════════════
// APP COLORS (legacy — kept for backward compatibility)
// ══════════════════════════════════════════════════════════════════════════════

object AppColors {
    // Light
    val successLight            = LightColors.success
    val onSuccessLight          = LightColors.onSuccess
    val successContainerLight   = LightColors.successContainer
    val onSuccessContainerLight = LightColors.onSuccessContainer
    val blueLight               = LightColors.blue
    val onBlueLight             = LightColors.onBlue
    val blueContainerLight      = LightColors.blueContainer
    val onBlueContainerLight    = LightColors.onBlueContainer
    val redLight                = LightColors.red
    val onRedLight              = LightColors.onRed
    val redContainerLight       = LightColors.redContainer
    val onRedContainerLight     = LightColors.onRedContainer
    val yellowLight             = LightColors.yellow
    val onYellowLight           = LightColors.onYellow
    val yellowContainerLight    = LightColors.yellowContainer
    val onYellowContainerLight  = LightColors.onYellowContainer
    val surfaceContainerLowestLight  = LightColors.surfaceContainerLowest
    val surfaceContainerLowLight     = LightColors.surfaceContainerLow
    val surfaceContainerLight        = LightColors.surfaceContainer
    val surfaceContainerHighLight    = LightColors.surfaceContainerHigh
    val surfaceContainerHighestLight = LightColors.surfaceContainerHighest
    val sourceColorLight             = LightColors.sourceColor

    // Dark
    val successDark             = DarkColors.success
    val onSuccessDark           = DarkColors.onSuccess
    val successContainerDark    = DarkColors.successContainer
    val onSuccessContainerDark  = DarkColors.onSuccessContainer
    val blueDark                = DarkColors.blue
    val onBlueDark              = DarkColors.onBlue
    val blueContainerDark       = DarkColors.blueContainer
    val onBlueContainerDark     = DarkColors.onBlueContainer
    val redDark                 = DarkColors.red
    val onRedDark               = DarkColors.onRed
    val redContainerDark        = DarkColors.redContainer
    val onRedContainerDark      = DarkColors.onRedContainer
    val yellowDark              = DarkColors.yellow
    val onYellowDark            = DarkColors.onYellow
    val yellowContainerDark     = DarkColors.yellowContainer
    val onYellowContainerDark   = DarkColors.onYellowContainer
    val surfaceContainerLowestDark  = DarkColors.surfaceContainerLowest
    val surfaceContainerLowDark     = DarkColors.surfaceContainerLow
    val surfaceContainerDark        = DarkColors.surfaceContainer
    val surfaceContainerHighDark    = DarkColors.surfaceContainerHigh
    val surfaceContainerHighestDark = DarkColors.surfaceContainerHighest
    val sourceColorDark             = DarkColors.sourceColor
}

// ══════════════════════════════════════════════════════════════════════════════
// EXTENDED COLOR CLASS (CompositionLocal)
// ══════════════════════════════════════════════════════════════════════════════

data class ExtendedColors(
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val blue: Color,
    val onBlue: Color,
    val blueContainer: Color,
    val onBlueContainer: Color,
    val red: Color,
    val onRed: Color,
    val redContainer: Color,
    val onRedContainer: Color,
    val yellow: Color,
    val onYellow: Color,
    val yellowContainer: Color,
    val onYellowContainer: Color,
    val surfaceBright: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val surfaceDim: Color,
    val sourceColor: Color,
    val primaryFixed: Color,
    val primaryFixedDim: Color,
    val onPrimaryFixed: Color,
    val onPrimaryFixedVariant: Color,
    val secondaryFixed: Color,
    val secondaryFixedDim: Color,
    val onSecondaryFixed: Color,
    val onSecondaryFixedVariant: Color,
    val tertiaryFixed: Color,
    val tertiaryFixedDim: Color,
    val onTertiaryFixed: Color,
    val onTertiaryFixedVariant: Color,
)

val LightExtendedColors = ExtendedColors(
    success                 = LightColors.success,
    onSuccess               = LightColors.onSuccess,
    successContainer        = LightColors.successContainer,
    onSuccessContainer      = LightColors.onSuccessContainer,
    blue                    = LightColors.blue,
    onBlue                  = LightColors.onBlue,
    blueContainer           = LightColors.blueContainer,
    onBlueContainer         = LightColors.onBlueContainer,
    red                     = LightColors.red,
    onRed                   = LightColors.onRed,
    redContainer            = LightColors.redContainer,
    onRedContainer          = LightColors.onRedContainer,
    yellow                  = LightColors.yellow,
    onYellow                = LightColors.onYellow,
    yellowContainer         = LightColors.yellowContainer,
    onYellowContainer       = LightColors.onYellowContainer,
    surfaceBright           = LightColors.surfaceBright,
    surfaceContainerLowest  = LightColors.surfaceContainerLowest,
    surfaceContainerLow     = LightColors.surfaceContainerLow,
    surfaceContainer        = LightColors.surfaceContainer,
    surfaceContainerHigh    = LightColors.surfaceContainerHigh,
    surfaceContainerHighest = LightColors.surfaceContainerHighest,
    surfaceDim              = LightColors.surfaceDim,
    sourceColor             = LightColors.sourceColor,
    primaryFixed            = LightColors.primaryFixed,
    primaryFixedDim         = LightColors.primaryFixedDim,
    onPrimaryFixed          = LightColors.onPrimaryFixed,
    onPrimaryFixedVariant   = LightColors.onPrimaryFixedVariant,
    secondaryFixed          = LightColors.secondaryFixed,
    secondaryFixedDim       = LightColors.secondaryFixedDim,
    onSecondaryFixed        = LightColors.onSecondaryFixed,
    onSecondaryFixedVariant = LightColors.onSecondaryFixedVariant,
    tertiaryFixed           = LightColors.tertiaryFixed,
    tertiaryFixedDim        = LightColors.tertiaryFixedDim,
    onTertiaryFixed         = LightColors.onTertiaryFixed,
    onTertiaryFixedVariant  = LightColors.onTertiaryFixedVariant,
)

val DarkExtendedColors = ExtendedColors(
    success                 = DarkColors.success,
    onSuccess               = DarkColors.onSuccess,
    successContainer        = DarkColors.successContainer,
    onSuccessContainer      = DarkColors.onSuccessContainer,
    blue                    = DarkColors.blue,
    onBlue                  = DarkColors.onBlue,
    blueContainer           = DarkColors.blueContainer,
    onBlueContainer         = DarkColors.onBlueContainer,
    red                     = DarkColors.red,
    onRed                   = DarkColors.onRed,
    redContainer            = DarkColors.redContainer,
    onRedContainer          = DarkColors.onRedContainer,
    yellow                  = DarkColors.yellow,
    onYellow                = DarkColors.onYellow,
    yellowContainer         = DarkColors.yellowContainer,
    onYellowContainer       = DarkColors.onYellowContainer,
    surfaceBright           = DarkColors.surfaceBright,
    surfaceContainerLowest  = DarkColors.surfaceContainerLowest,
    surfaceContainerLow     = DarkColors.surfaceContainerLow,
    surfaceContainer        = DarkColors.surfaceContainer,
    surfaceContainerHigh    = DarkColors.surfaceContainerHigh,
    surfaceContainerHighest = DarkColors.surfaceContainerHighest,
    surfaceDim              = DarkColors.surfaceDim,
    sourceColor             = DarkColors.sourceColor,
    primaryFixed            = DarkColors.primaryFixed,
    primaryFixedDim         = DarkColors.primaryFixedDim,
    onPrimaryFixed          = DarkColors.onPrimaryFixed,
    onPrimaryFixedVariant   = DarkColors.onPrimaryFixedVariant,
    secondaryFixed          = DarkColors.secondaryFixed,
    secondaryFixedDim       = DarkColors.secondaryFixedDim,
    onSecondaryFixed        = DarkColors.onSecondaryFixed,
    onSecondaryFixedVariant = DarkColors.onSecondaryFixedVariant,
    tertiaryFixed           = DarkColors.tertiaryFixed,
    tertiaryFixedDim        = DarkColors.tertiaryFixedDim,
    onTertiaryFixed         = DarkColors.onTertiaryFixed,
    onTertiaryFixedVariant  = DarkColors.onTertiaryFixedVariant,
)

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }

val MaterialTheme.extendedColors: ExtendedColors
    @Composable get() = LocalExtendedColors.current

// ══════════════════════════════════════════════════════════════════════════════
// MATERIAL3 COLOR SCHEMES
// ══════════════════════════════════════════════════════════════════════════════

private val LightColorScheme = lightColorScheme(
    primary                = LightColors.primary,
    onPrimary              = LightColors.onPrimary,
    primaryContainer       = LightColors.primaryContainer,
    onPrimaryContainer     = LightColors.onPrimaryContainer,
    inversePrimary         = LightColors.inversePrimary,
    secondary              = LightColors.secondary,
    onSecondary            = LightColors.onSecondary,
    secondaryContainer     = LightColors.secondaryContainer,
    onSecondaryContainer   = LightColors.onSecondaryContainer,
    tertiary               = LightColors.tertiary,
    onTertiary             = LightColors.onTertiary,
    tertiaryContainer      = LightColors.tertiaryContainer,
    onTertiaryContainer    = LightColors.onTertiaryContainer,
    background             = LightColors.background,
    onBackground           = LightColors.onBackground,
    surface                = LightColors.surface,
    onSurface              = LightColors.onSurface,
    surfaceVariant         = LightColors.surfaceVariant,
    onSurfaceVariant       = LightColors.onSurfaceVariant,
    surfaceTint            = LightColors.surfaceTint,
    inverseSurface         = LightColors.inverseSurface,
    inverseOnSurface       = LightColors.inverseOnSurface,
    outline                = LightColors.outline,
    outlineVariant         = LightColors.outlineVariant,
    error                  = LightColors.error,
    onError                = LightColors.onError,
    errorContainer         = LightColors.errorContainer,
    onErrorContainer       = LightColors.onErrorContainer,
    scrim                  = LightColors.scrim,
)

private val DarkColorScheme = darkColorScheme(
    primary                = DarkColors.primary,
    onPrimary              = DarkColors.onPrimary,
    primaryContainer       = DarkColors.primaryContainer,
    onPrimaryContainer     = DarkColors.onPrimaryContainer,
    inversePrimary         = DarkColors.inversePrimary,
    secondary              = DarkColors.secondary,
    onSecondary            = DarkColors.onSecondary,
    secondaryContainer     = DarkColors.secondaryContainer,
    onSecondaryContainer   = DarkColors.onSecondaryContainer,
    tertiary               = DarkColors.tertiary,
    onTertiary             = DarkColors.onTertiary,
    tertiaryContainer      = DarkColors.tertiaryContainer,
    onTertiaryContainer    = DarkColors.onTertiaryContainer,
    background             = DarkColors.background,
    onBackground           = DarkColors.onBackground,
    surface                = DarkColors.surface,
    onSurface              = DarkColors.onSurface,
    surfaceVariant         = DarkColors.surfaceVariant,
    onSurfaceVariant       = DarkColors.onSurfaceVariant,
    surfaceTint            = DarkColors.surfaceTint,
    inverseSurface         = DarkColors.inverseSurface,
    inverseOnSurface       = DarkColors.inverseOnSurface,
    outline                = DarkColors.outline,
    outlineVariant         = DarkColors.outlineVariant,
    error                  = DarkColors.error,
    onError                = DarkColors.onError,
    errorContainer         = DarkColors.errorContainer,
    onErrorContainer       = DarkColors.onErrorContainer,
    scrim                  = DarkColors.scrim,
)

// ══════════════════════════════════════════════════════════════════════════════
// THEME ENTRY POINT
// ══════════════════════════════════════════════════════════════════════════════

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
        else      -> LightColorScheme
    }

    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = Typography,
            content     = content
        )
    }
}