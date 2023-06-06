package com.itielfelix.examenmegafinal.ui.main

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itielfelix.examenmegafinal.ui.theme.ExamenMegaFinalTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.itielfelix.examenmegafinal.R
import com.itielfelix.examenmegafinal.ui.additional.FilterViewModel
import com.itielfelix.examenmegafinal.ui.home.*
import com.itielfelix.examenmegafinal.ui.theme.Purple200
import com.itielfelix.examenmegafinal.util.Constants.Companion.BrownColorPalette
import com.itielfelix.examenmegafinal.util.Constants.Companion.DarkColorPalette
import com.itielfelix.examenmegafinal.util.Constants.Companion.actualPalette
import com.itielfelix.examenmegafinal.util.DatabaseHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.selects.select

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var history: DatabaseHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var selectedTheme by remember{mutableStateOf(actualPalette)}
            var themeSelected by remember{mutableStateOf(false)}
            var isDarkTheme by remember { mutableStateOf(true) }
            ExamenMegaFinalTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
                    val themeViewModel = viewModel(modelClass = ThemeViewModel::class.java)
                    homeViewModel.initializeLists()
                    val filterViewModel = viewModel(modelClass = FilterViewModel::class.java)
                    val navController = rememberNavController()
                    val logo = if (!isSystemInDarkTheme()) R.drawable.light_logo else R.drawable.dark_logo
                    history = DatabaseHelper(this)
                    val configuration = LocalConfiguration.current

                    // When orientation is Landscape

                    NavHost(navController = navController, startDestination = "index") {
                        composable("index"){
                            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                                Box(Modifier.padding(0.dp)
                                    .height(200.dp).width(800.dp)) {
                                    Image(
                                        painterResource(logo),
                                        contentDescription = "light logo",
                                        Modifier.size(800.dp)
                                    )
                                }
                                Button(onClick = {navController.navigate("home")}, colors = ButtonDefaults.buttonColors(backgroundColor = themeViewModel.state.colorPallete.primary)){
                                    Text("Go to Home")
                                }
                                Spacer(Modifier.padding(40.dp))
                                Text("Do you want a new color? Click here!")
                                Switch(
                                        checked = themeSelected,
                                onCheckedChange = {
                                    themeSelected = it;
                                    if(themeSelected) themeViewModel.changeTheme(BrownColorPalette)
                                    else themeViewModel.changeTheme(DarkColorPalette)
                                }
                                )
                            }
                        }
                        composable("home"){
                           when (configuration.orientation) {
                                Configuration.ORIENTATION_LANDSCAPE -> {
                                    BoxWithLayout {
                                        Row(Modifier.weight(1f)) {
                                            Column(Modifier.width(400.dp)) {
                                                HomePage(homeViewModel = homeViewModel, navController, history, horizontal = true, colorPallete = themeViewModel.state.colorPallete)

                                            }
                                            Column(Modifier.width(400.dp)) {
                                                DetailsPage(homeViewModel = homeViewModel, navController, themeViewModel.state.colorPallete)
                                            }

                                        }
                                    }
                                }
                                else ->{
                                    HomePage(homeViewModel = homeViewModel, navController, history, horizontal = false, colorPallete = themeViewModel.state.colorPallete)
                                }
                            }


                        }
                        composable("details"){
                            DetailsPage(homeViewModel = homeViewModel, navController , themeViewModel.state.colorPallete)
                        }
                        composable("filter"){
                            FilterPage(homeViewModel = homeViewModel, filterViewModel = filterViewModel, navController = navController, colorPallete = themeViewModel.state.colorPallete)
                        }
                        composable("database"){
                            when (configuration.orientation) {
                                Configuration.ORIENTATION_LANDSCAPE -> {
                                    BoxWithLayout {
                                        Row(Modifier.weight(1f)) {
                                            Column(Modifier.width(400.dp)) {
                                                DatabasePage(dbHelper = history, homeViewModel = homeViewModel,{},{navController.popBackStack()}, themeViewModel.state.colorPallete)

                                            }
                                            Column(Modifier.width(400.dp)) {
                                                DetailsPage(homeViewModel = homeViewModel, navController, themeViewModel.state.colorPallete)
                                            }

                                        }
                                    }
                                }
                                else ->{
                                    DatabasePage(dbHelper = history, homeViewModel = homeViewModel,{ navController.navigate("details")},{navController.popBackStack()},themeViewModel.state.colorPallete)
                                }
                            }
                        }
                    }
                    }

            }

        }

    }

    @Composable
    fun BoxWithLayout(content: @Composable RowScope.() ->Unit){
        Row(){
            content()
        }
    }

    @Composable
    fun MainPage(){

    }

}