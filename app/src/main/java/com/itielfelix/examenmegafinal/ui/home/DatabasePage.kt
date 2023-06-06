package com.itielfelix.examenmegafinal.ui.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itielfelix.examenmegafinal.ui.theme.ExamenMegaFinalTheme
import com.itielfelix.examenmegafinal.ui.theme.Purple200
import com.itielfelix.examenmegafinal.util.Constants
import com.itielfelix.examenmegafinal.util.DatabaseHelper

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatabasePage(dbHelper: DatabaseHelper,homeViewModel: HomeViewModel, navigation: () ->Unit, goBack: () ->Unit, colorPallete: Colors) {
    var games by remember{ mutableStateOf(dbHelper.listData()) }
    var updateList by remember {mutableStateOf(true)}
    var backgroundColor = if(colorPallete.primary!= Purple200) colorPallete.primaryVariant else colorPallete.background
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().background(backgroundColor)) {

        Text(modifier = Modifier.padding(5.dp),text = "Saved Games", fontWeight = FontWeight.Bold, fontSize = 25.sp, fontFamily = FontFamily.Serif)
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()){
        }

        LazyColumn {
            items(games){ game ->
                GameCard(game = game, registered = dbHelper.isRegistered(game.id), onClick ={homeViewModel.updateGame(game); navigation()},
                    addItemToDb = {}, deleteItem = {dbHelper.deleteRow(game.id); games= dbHelper.listData(); updateList = !updateList ;}, isFromDb = dbHelper.getSavedDate(game.id), colorPallete)
            }

        }
        BackHandler() {
            goBack()
            homeViewModel.clearGame()
        }
    }
}

