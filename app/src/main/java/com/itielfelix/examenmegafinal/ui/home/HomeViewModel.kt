package com.itielfelix.examenmegafinal.ui.home

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itielfelix.examenmegafinal.domain.GetGamesUseCase
import com.itielfelix.examenmegafinal.domain.item.GameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class HomeViewModel @Inject constructor(private val getGamesUseCase: GetGamesUseCase) : ViewModel() {

    private var _games = MutableStateFlow(emptyList<GameItem>())
    var _games2 = MutableStateFlow(emptyList<GameItem>())
    val games: StateFlow<List<GameItem>> get() = _games
    val updated : Boolean = true
    var game by mutableStateOf(GameState())
    lateinit var genres:List<String>
    lateinit var platforms:List<String>
    lateinit var publishers:List<String>
    lateinit var developers:List<String>
    init {
        getGames()
    }

    private fun getGames() {
        viewModelScope.launch {
            try {
                val games = getGamesUseCase()
                _games.value = games
                _games2.value = games
            } catch (_: Exception) {}
        }
    }
    fun initializeLists(){
        platforms = getAllPlatforms()
        genres = getAllGenres()
        developers = getAllDevelopers()
        publishers = getAllPublishers()
    }
    private fun getAllGenres():List<String>{
        return games.value.map {it.genre}.distinct()
    }
    private fun getAllPlatforms():List<String>{
        return games.value.map {it.platform}.distinct()
    }
    private fun getAllPublishers():List<String>{
        return games.value.map {it.publisher}.distinct()
    }
    private fun getAllDevelopers():List<String>{
        return games.value.map {it.developer}.distinct()
    }
    fun search(text:String){
        val newList = _games2.value.filter { it.title.contains(text)}
        _games.value = newList
        if(text == "") _games.value = _games2.value
    }

    fun applyFilter(genre:String, platform:String, publisher:String){
        if(genre == "" && platform =="" && publisher == "" ) _games.value = _games2.value
        else {
            var newList = emptyList<GameItem>()
            if (genre != "") newList = _games2.value.filter { it.genre == genre }
            if (platform != "") {
                if(newList.isEmpty()) newList = _games2.value.filter { it.platform == platform }
                else newList = newList.filter { it.platform == platform }
            }
            if (publisher != "") {
                if(newList.isEmpty()) newList = _games2.value.filter { it.publisher == publisher }
                else newList = newList.filter { it.publisher == publisher }
            }
            _games.value = newList
        }
    }

    fun updateGame(newGame: GameItem){
        game = game.copy(newGame.id, newGame.title, newGame.thumbnail, newGame.short_description,
        newGame.developer,newGame.freetogame_profile_url, newGame.game_url, newGame.genre, newGame.platform,
        newGame.publisher,newGame.release_date)
    }
    fun clearGame(){
        game = game.copy(0)
    }
}
