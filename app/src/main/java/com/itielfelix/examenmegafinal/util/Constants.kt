package com.itielfelix.examenmegafinal.util

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.itielfelix.examenmegafinal.ui.theme.*

class Constants {
    companion object {
        const val BASE_URL = "https://www.freetogame.com/api/"
        const val GAMES_ENDPOINT = "games?sort-by=alphabetical"

        var LightPalette = lightColors(
            primary = Orange500,
            primaryVariant = Orange700,
            secondary = LimeGreen700
        )
        val DarkColorPalette = darkColors(
            primary = Purple200,
            primaryVariant = Purple700,
            secondary = Teal200
        )
        val BrownColorPalette = lightColors(
            primary = Color	(104,122,158),
            primaryVariant = Color(133, 171, 223, 255),
            secondary = Color		(214,188,111),
            background = Color		(170,170,170)
        )
        val actualPalette = DarkColorPalette
    }
}