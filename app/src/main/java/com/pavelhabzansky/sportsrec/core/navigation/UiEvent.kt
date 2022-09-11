package com.pavelhabzansky.sportsrec.core.navigation

import com.pavelhabzansky.sportsrec.core.ui.UiText

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    object NavigateUp : UiEvent()

    data class Snackbar(val text: UiText) : UiEvent()
}