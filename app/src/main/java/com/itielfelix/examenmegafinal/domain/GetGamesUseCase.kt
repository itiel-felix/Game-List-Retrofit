package com.itielfelix.examenmegafinal.domain

import com.itielfelix.examenmegafinal.domain.item.GameItem
import com.itielfelix.examenmegafinal.repo.GameRepository
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(private val gameRepository: GameRepository) {
    suspend operator fun invoke(): List<GameItem> {

        return gameRepository.getGames()

    }
}