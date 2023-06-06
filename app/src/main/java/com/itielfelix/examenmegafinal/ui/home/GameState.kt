package com.itielfelix.examenmegafinal.ui.home

import androidx.compose.runtime.MutableState

data class GameState (
    val id: Int = 0,
    val title: String = "",
    val thumbnail: String = "", //2
    val short_description: String = "",// 3
    val developer: String = "", //6
    val freetogame_profile_url: String = "",
    val game_url: String = "",
    val genre: String = "", //5
    val platform: String = "",
    val publisher: String = "",//7
    val release_date: String = "", //4
)
