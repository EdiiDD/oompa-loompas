package com.edy.oompaloompas.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.edy.oompaloompas.presentation.navigation.BottomAppBarOompaLoompas
import com.edy.oompaloompas.presentation.navigation.OompaLoompasNavigation
import com.edy.oompaloompas.presentation.ui.theme.OompaLoompasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            OompaLoompasTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    bottomBar = {
                        BottomAppBarOompaLoompas(navController)
                    }
                ) {
                    OompaLoompasNavigation(
                        navController = navController,
                        padding = it
                    )
                }
            }
        }
    }
}