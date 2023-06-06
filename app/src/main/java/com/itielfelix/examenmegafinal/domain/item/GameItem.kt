package com.itielfelix.examenmegafinal.domain.item

import com.itielfelix.examenmegafinal.data.remote.model.GameModel

data class GameItem(
    val id: Int,
    val title: String,
    val thumbnail: String, //2
    val short_description: String,// 3
    val developer: String, //6
    val freetogame_profile_url: String,
    val game_url: String,
    val genre: String, //5
    val platform: String,
    val publisher: String,//7
    val release_date: String, //4
)

fun GameModel.toGameItem() = GameItem(id, title, thumbnail, short_description, developer, freetogame_profile_url, game_url, genre, platform, publisher, release_date)
