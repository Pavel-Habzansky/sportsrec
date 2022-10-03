package com.pavelhabzansky.sportsrec.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pavelhabzansky.sportsrec.core.navigation.Route
import com.pavelhabzansky.sportsrec.core.navigation.navigate
import com.pavelhabzansky.sportsrec.core.ui.theme.SportsRecTheme
import com.pavelhabzansky.sportsrec.features.auth.AuthScreen
import com.pavelhabzansky.sportsrec.features.new_record.NewRecordScreen
import com.pavelhabzansky.sportsrec.features.record_detail.RecordDetailScreen
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
                                navigateUp = navController::navigateUp,
                                snackbarHostState = scaffoldState.snackbarHostState
                            )
                        }
                        composable(
                            "${Route.RECORD_DETAIL}/{recordId}",
                            arguments = listOf(navArgument("recordId") {
                                type = NavType.StringType
                            })
                        ) {
                            RecordDetailScreen(
                                navigateUp = navController::navigateUp
                            )
                        }
                        composable(Route.NEW_RECORD) {
                            NewRecordScreen(
                                onNavigate = navController::navigate,
                                navigateUp = navController::navigateUp,
                                snackbarHostState = scaffoldState.snackbarHostState
                            )
                        }
                    }
                }
            }
        }
    }
}
