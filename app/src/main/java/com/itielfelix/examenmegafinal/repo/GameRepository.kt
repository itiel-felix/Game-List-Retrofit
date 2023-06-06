package com.itielfelix.examenmegafinal.repo

import com.itielfelix.examenmegafinal.data.remote.GameService
import com.itielfelix.examenmegafinal.domain.item.GameItem
import com.itielfelix.examenmegafinal.domain.item.toGameItem
import javax.inject.Inject

class GameRepository @Inject constructor(private val gameService: GameService) {

    suspend fun getGames(): List<GameItem> {
        return gameService.getGames().map {
            it.toGameItem()
        }

    }

}