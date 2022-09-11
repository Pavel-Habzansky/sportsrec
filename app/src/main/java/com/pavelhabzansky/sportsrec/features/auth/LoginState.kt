package com.pavelhabzansky.sportsrec.features.auth

data class LoginState(
    val email: String = "",
    val password: String = ""
)