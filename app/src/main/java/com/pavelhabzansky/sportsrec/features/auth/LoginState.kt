package com.pavelhabzansky.sportsrec.features.auth

data class LoginState(
    val email: String = "habzansky.pavel@gmail.com",
    val password: String = "12345678",
    val screenState: AuthScreenState = AuthScreenState.Idle
)