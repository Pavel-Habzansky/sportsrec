package com.pavelhabzansky.sportsrec.core.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigate(event: UiEvent.Navigate) {
    navigate(event.route)
}