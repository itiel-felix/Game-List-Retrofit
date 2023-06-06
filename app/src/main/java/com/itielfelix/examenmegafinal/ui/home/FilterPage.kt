package com.itielfelix.examenmegafinal.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itielfelix.examenmegafinal.ui.additional.FilterViewModel
import com.itielfelix.examenmegafinal.ui.theme.ExamenMegaFinalTheme
import com.itielfelix.examenmegafinal.ui.theme.Purple200
import com.itielfelix.examenmegafinal.util.Constants

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterPage(filterViewModel: FilterViewModel, homeViewModel: HomeViewModel, navController: NavController, colorPallete: Colors){
    val state = filterViewModel.state
    var genre by remember {mutableStateOf(state.genre)}
    var platform  by remember {mutableStateOf(state.platform)}
    var publisher  by remember {mutableStateOf(state.publisher)}
    //homeViewModel.applyFilter(genre,platform,publisher)

    val releaseYear by remember {mutableStateOf(state.releaseYear)}

    val genres = homeViewModel._games2.value.map {it.genre}.distinct()
    val platforms = homeViewModel._games2.value.map {it.platform}.distinct()
    val publishers = homeViewModel._games2.value.map {it.publisher}.distinct()

    var expandedGenre by remember{ mutableStateOf(false) }
    var expandedPlatform by remember{ mutableStateOf(false) }
    var expandedPublisher by remember{ mutableStateOf(false) }
    val backgroundColor = if(colorPallete.primary!= Purple200) colorPallete.primary else colorPallete.background
    val buttonColor = if(colorPallete.primary!= Purple200) colorPallete.primaryVariant else colorPallete.primary

    Box(Modifier.fillMaxSize().background(backgroundColor),contentAlignment = Alignment.Center){
        Column(Modifier.fillMaxWidth(),verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Filters", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 50.dp))
            ExposedDropdownMenuBox(expanded = expandedGenre , onExpandedChange = {expandedGenre = !expandedGenre}) {
            TextField(value = genre,
                onValueChange = {},
                readOnly = true,
                label = { Text("Game Genres") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenre)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(expanded = expandedGenre, onDismissRequest = {expandedGenre = false}
            ) {
               genres.forEach { selectedOption ->
                    DropdownMenuItem(onClick = {genre = selectedOption; expandedGenre = false; }) {
                        Text(text= selectedOption)
                    }
                }
            }
        }

            ExposedDropdownMenuBox(expanded = expandedPlatform , onExpandedChange = {expandedPlatform = !expandedPlatform}) {
                TextField(value = platform,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Game Platforms") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPlatform)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(expanded = expandedPlatform, onDismissRequest = {expandedPlatform = false}
                ) {
                    platforms.forEach { selectedOption ->
                        DropdownMenuItem(onClick = {platform = selectedOption; expandedPlatform = false;}) {
                            Text(text= selectedOption)
                        }
                    }
                }
            }

            ExposedDropdownMenuBox(expanded = expandedPublisher , onExpandedChange = {expandedPublisher = !expandedPublisher}) {
                TextField(value = publisher,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Game Publishers") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPublisher)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(expanded = expandedPublisher, onDismissRequest = {expandedPublisher = false}
                ) {
                    publishers.forEach { selectedOption ->
                        DropdownMenuItem(onClick = {publisher = selectedOption; expandedPublisher = false;}) {
                            Text(text= selectedOption)
                        }
                    }
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                Button(onClick = {
                    if(genre != "-") filterViewModel.changeGenre(genre)
                    if(platform !="-") filterViewModel.changePlatform(platform)
                    if(publisher !="-") filterViewModel.changePublisher(publisher)
                    // Log.i("info","value of genre")
                    homeViewModel.applyFilter(genre,platform,publisher)
                    navController.popBackStack()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)) {
                    Text("Apply")
                }
                Button(onClick = {
                    navController.popBackStack()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)){
                    Text("Cancel")
                }
                Button(onClick = {
                    filterViewModel.changeGenre("")
                    filterViewModel.changePlatform("")
                    filterViewModel.changePublisher("")
                    homeViewModel.applyFilter("","","")
                    navController.popBackStack()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)
                ){
                    Text("Clear")
                }
            }

    }
    }
}
