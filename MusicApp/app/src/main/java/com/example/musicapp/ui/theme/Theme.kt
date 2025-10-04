package com.example.musicapp.ui.theme

// Removed M3 imports: android.app.Activity, android.os.Build, androidx.compose.material3.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme // M2 MaterialTheme
import androidx.compose.material.Shapes // Import for Shapes class
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
// Removed M3 import: androidx.compose.ui.platform.LocalContext (not used for M2 dynamic colors)

// Define M2 color palettes using colors from Color.kt
private val DarkColorPalette = darkColors(
    primary = Purple200, // Example M2 dark theme primary
    primaryVariant = Purple700,
    secondary = Teal200
    // You can define other colors like background, surface, onPrimary, etc.
)

private val LightColorPalette = lightColors(
    primary = Purple500, // Example M2 light theme primary
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default M2 colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MusicAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    // M3 dynamic color logic removed as it's not a standard M2 feature.
    // If you need specific platform-based colors for M2, that would be custom logic.

    MaterialTheme(
        colors = colors, // M2 uses 'colors' not 'colorScheme'
        typography = Typography, // Assumes Typography.kt is M2 compatible or will be adjusted
        shapes = Shapes(),       // Corrected: Use an instance of Shapes
        content = content
    )
}
