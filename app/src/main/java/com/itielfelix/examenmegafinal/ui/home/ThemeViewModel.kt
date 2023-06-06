package com.itielfelix.examenmegafinal.ui.home

import androidx.compose.material.Colors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ThemeViewModel: ViewModel() {
    var state by mutableStateOf(ThemeState())
    private set

    fun changeTheme(newTheme: Colors){
        state = state.copy(colorPallete = newTheme)
    }
}