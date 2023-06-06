package com.itielfelix.examenmegafinal.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.itielfelix.examenmegafinal.domain.item.GameItem
import com.itielfelix.examenmegafinal.ui.theme.Orange500
import com.itielfelix.examenmegafinal.ui.theme.Purple200
import com.itielfelix.examenmegafinal.util.DatabaseHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(homeViewModel: HomeViewModel, navController: NavController, dbHelper: DatabaseHelper, horizontal:Boolean, colorPallete:Colors) {
        val games by homeViewModel.games.collectAsState()
        var text by remember { mutableStateOf("") }
        var background = if(colorPallete.primary!= Purple200) colorPallete.primaryVariant else colorPallete.background
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().background(background)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigate("filter") },
                    Modifier.padding(top = 10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
                TextField(
                    value = text,
                    onValueChange = { text = it; homeViewModel.search(it) },
                    Modifier
                        .padding(top = 15.dp)
                        .width(250.dp),
                    placeholder = {
                        Text("Search", color = colorPallete.primary)
                    }
                )
                IconButton(
                    onClick = { navController.navigate("database"); homeViewModel.clearGame() },
                    Modifier.padding(top = 10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "")
                }
            }

            LazyColumn {
                items(games) { game ->
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    GameCard(game = game, registered = dbHelper.isRegistered(game.id),
                        onClick = {
                            if (!horizontal) navController.navigate("details");
                            homeViewModel.updateGame(game)
                        },
                        addItemToDb = {
                            dbHelper.addRow(
                                game.id,
                                game.title,
                                game.thumbnail,
                                game.short_description,
                                game.developer,
                                game.game_url,
                                game.genre,
                                game.platform,
                                game.publisher,
                                game.release_date,
                                LocalDateTime.now().format(formatter).toString(),
                                game.freetogame_profile_url
                            )
                        }, { dbHelper.deleteRow(game.id) }, colorPallete = colorPallete)
                }

            }
        }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameCard(game: GameItem,registered:Boolean, onClick: () -> Unit, addItemToDb: () -> Unit, deleteItem: () ->Unit, isFromDb:String ="",
colorPallete: Colors) {
    var added by remember{mutableStateOf(registered)}
    val image = rememberImagePainter(data = game.thumbnail)
    val backgroundCard = if(colorPallete.primary != Purple200) colorPallete.primary else colorPallete.background
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
            .fillMaxSize(),
        onClick = { onClick() },
        backgroundColor = backgroundCard

    ) {

        Column(verticalArrangement = Arrangement.Center) {

            Row(Modifier.height(100.dp)) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                )
                Column() {
                    Row() {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround
                        ) {

                            Row(Modifier.width(160.dp)){
                                Text(text = game.title, fontWeight = FontWeight.Bold, fontSize = 20.sp,maxLines = 1,overflow = TextOverflow.Ellipsis)
                            }

                            Row(Modifier.width(160.dp)) {
                                Text("Genre", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(
                                    "Release date",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(start = 20.dp)
                                )
                            }
                            Row(
                                Modifier.width(140.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(game.genre, fontWeight = FontWeight.Light, fontSize = 12.sp)
                                Text(
                                    game.release_date,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 20.dp)
                                )
                            }
                            if (isFromDb != "") {
                                Row(Modifier.padding(top=5.dp)) {
                                    Text("Saved on $isFromDb", fontSize = 8.sp)
                                }
                            }

                        }
                        Column(
                            Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (!added) {
                                IconButton(onClick = { addItemToDb(); added=!added}) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                                }
                            } else {
                                IconButton(onClick = { deleteItem();added=!added}) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                }


            }

        }

    }
}
