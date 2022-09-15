package com.pavelhabzansky.sportsrec.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import com.pavelhabzansky.sportsrec.core.ui.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun sendSnack(message: UiText) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(UiEvent.Snackbar(message))
        }
    }
}