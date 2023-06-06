package com.itielfelix.examenmegafinal.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itielfelix.examenmegafinal.ui.theme.ExamenMegaFinalTheme
import com.itielfelix.examenmegafinal.util.Constants

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Index(navigation: () ->Unit = {}, choosedTheme: () ->Unit ={}) {
    var themeSelected by remember { mutableStateOf(true) }
    Column(Modifier.fillMaxSize()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 10.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("☀️")
                    Switch(
                        checked = themeSelected,
                        onCheckedChange = {
                            themeSelected = it;
                        }
                    )
                    Text("🌘")
                    Button(onClick = {navigation()}) {
                        Text(text = "Go to Main Page")
                    }

        }
    }
}