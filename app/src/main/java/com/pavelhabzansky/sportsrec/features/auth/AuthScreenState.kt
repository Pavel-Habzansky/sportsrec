package com.pavelhabzansky.sportsrec.features.auth

sealed class AuthScreenState {
    object Idle : AuthScreenState()
    object Loading : AuthScreenState()
}