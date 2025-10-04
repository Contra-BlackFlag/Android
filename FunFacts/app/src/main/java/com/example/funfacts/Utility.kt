package com.example.funfacts

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

object Utility {
    fun generateColor() : Color{
        return Color(
            red = Random.nextFloat(),
            green = Random.nextFloat() ,
            blue = Random.nextFloat(),
            alpha = 1f
        )
    }
}