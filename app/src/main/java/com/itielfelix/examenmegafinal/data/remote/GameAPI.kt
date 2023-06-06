package com.itielfelix.examenmegafinal.data.remote

import com.itielfelix.examenmegafinal.data.remote.model.GameModel
import com.itielfelix.examenmegafinal.util.Constants.Companion.GAMES_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

interface GameAPI {

    @GET(GAMES_ENDPOINT)
    suspend fun getGames(): Response<List<GameModel>>
}