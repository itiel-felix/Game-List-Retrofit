package com.itielfelix.examenmegafinal.ui.additional

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FilterViewModel: ViewModel() {
    var state by mutableStateOf(FilterState())
    private set
    init {
        state = state.copy("","","","")
    }
    fun changeGenre(genre:String){
        state = state.copy(genre = genre)
    }
    fun changePlatform(platform:String){
        state = state.copy(platform = platform)
    }
    fun changePublisher(publisher:String){
        state = state.copy(publisher = publisher)
    }

}