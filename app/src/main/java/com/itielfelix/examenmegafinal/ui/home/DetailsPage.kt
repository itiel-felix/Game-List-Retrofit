package com.itielfelix.examenmegafinal.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.itielfelix.examenmegafinal.ui.theme.ExamenMegaFinalTheme
import com.itielfelix.examenmegafinal.ui.theme.Purple200
import com.itielfelix.examenmegafinal.util.Constants


@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailsPage (homeViewModel: HomeViewModel, navController: NavController, colorPalette: Colors){
    val game = homeViewModel.game
    val image = rememberImagePainter(data = game.thumbnail)
    val backgroundColor = if(colorPalette.primary != Purple200) colorPalette.primary else colorPalette.background
    val cardBackgroundColor = if(colorPalette.primary != Purple200) colorPalette.primary else colorPalette.background
    val elementColor = if(colorPalette.primary != Purple200) colorPalette.primaryVariant else colorPalette.background
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()).background(backgroundColor)) {
            Box() {
                if(game.id !=0) {
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                    Card(
                        Modifier
                            .padding(top = 180.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
                    ) {
                        Column(Modifier.fillMaxSize().background(cardBackgroundColor)) {
                            Row(
                                Modifier
                                    .padding(top = 30.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(game.title, fontWeight = FontWeight.Bold, fontSize = 30.sp)
                            }
                            Spacer(modifier = Modifier.padding(20.dp))
                            Column() {
                                elementInfo(label = "Description", value = game.short_description, elementColor)
                                elementInfo(label = "Genre", value = game.genre,elementColor)
                                elementInfo(label = "Platform", value = game.platform,elementColor)
                                elementInfo(label = "Release date", value = game.release_date,elementColor)
                                elementInfo(label = "Developer team", value = game.developer,elementColor)
                                elementInfo(label = "Publisher", value = game.publisher,elementColor)


                            }
                        }
                    }
                }else{
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(modifier = Modifier.padding(top = 130.dp),text = "No game selected!", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }

    }



@Composable
fun elementInfo(label: String, value:String, cardColor: Color){
    Card(
        Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 6.dp)
            .fillMaxWidth(), elevation = 2.dp,  shape = RoundedCornerShape(10)
    ){
        Column(Modifier.background(cardColor).padding(15.dp)) {
            Text(label, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(value, fontWeight = FontWeight.Light, fontSize = 12.sp)
        }

    }
}