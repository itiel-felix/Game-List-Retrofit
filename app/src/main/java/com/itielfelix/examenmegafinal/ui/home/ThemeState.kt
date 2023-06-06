package com.itielfelix.examenmegafinal.ui.home

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import com.itielfelix.examenmegafinal.ui.theme.Purple200
import com.itielfelix.examenmegafinal.ui.theme.Purple700
import com.itielfelix.examenmegafinal.ui.theme.Teal200

data class ThemeState (
    val colorPallete: Colors = darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        secondary = Teal200
    )
)