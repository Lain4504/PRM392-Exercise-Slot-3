package com.example.exerciseslot3.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MartfuryPrimary,
    secondary = MartfuryAccent,
    tertiary = MartfuryOrange,
    background = MartfuryBackground,
    surface = MartfuryBackground,
    onPrimary = MartfuryText,
    onSecondary = MartfuryBackground,
    onTertiary = MartfuryBackground,
    onBackground = MartfuryText,
    onSurface = MartfuryText
)

private val LightColorScheme = lightColorScheme(
    primary = MartfuryPrimary,
    secondary = MartfuryAccent,
    tertiary = MartfuryOrange,
    background = MartfuryBackground,
    surface = MartfuryBackground,
    onPrimary = MartfuryText,
    onSecondary = MartfuryBackground,
    onTertiary = MartfuryBackground,
    onBackground = MartfuryText,
    onSurface = MartfuryText
)

@Composable
fun ExerciseSlot3Theme(
    darkTheme: Boolean = false, // Force light theme for martfury app
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic color for consistent martfury branding
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme // Always use light theme for martfury app

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MartfuryHeaderYellow.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}