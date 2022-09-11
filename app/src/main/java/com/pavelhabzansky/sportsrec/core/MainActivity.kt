package com.pavelhabzansky.sportsrec.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pavelhabzansky.sportsrec.core.navigation.Route
import com.pavelhabzansky.sportsrec.core.navigation.navigate
import com.pavelhabzansky.sportsrec.core.ui.theme.SportsRecTheme
import com.pavelhabzansky.sportsrec.features.auth.AuthScreen
import com.pavelhabzansky.sportsrec.features.record_list.RecordsListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportsRecTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    NavHost(navController = navController, startDestination = Route.AUTH) {
                        composable(Route.AUTH) {
                            AuthScreen(
                                onNavigate = navController::navigate,
                                snackbarHostState = scaffoldState.snackbarHostState
                            )
                        }
                        composable(Route.RECORD_LIST) {
                            RecordsListScreen(
                                onNavigate = navController::navigate,
                            )
                        }
                        composable(Route.RECORD_DETAIL) {

                        }
                        composable(Route.NEW_RECORD) {

                        }
                    }
                }
            }
        }
    }
}
