package com.example.musicapp.ui.theme

import androidx.compose.material.Typography // Ensure this is the M2 Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with (M2 compatible)
val Typography = Typography(
    body1 = TextStyle( // Changed from bodyLarge to body1 (M2 style)
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default M2 text styles to override if needed:
    h1 = TextStyle(...),
    h2 = TextStyle(...),
    h3 = TextStyle(...),
    h4 = TextStyle(...),
    h5 = TextStyle(...),
    h6 = TextStyle(...),
    subtitle1 = TextStyle(...),
    subtitle2 = TextStyle(...),
    body2 = TextStyle(...),
    button = TextStyle(...),
    caption = TextStyle(...),
    overline = TextStyle(...)
    */
    // Example for a commented-out M3 titleLarge, if you were using it:
    /*
    h5 = TextStyle( // Changed from titleLarge to h5 (M2 equivalent)
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp, // Adjust sp values as needed for M2 scale
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    */
    // Example for a commented-out M3 labelSmall, if you were using it:
    /*
    caption = TextStyle( // Changed from labelSmall to caption (M2 equivalent)
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp, // Adjust sp values as needed for M2 scale
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
