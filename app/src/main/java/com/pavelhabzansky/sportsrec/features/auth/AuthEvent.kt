package com.pavelhabzansky.sportsrec.features.auth

sealed class AuthEvent {
    object SignInClick : AuthEvent()
    object SignUpClick : AuthEvent()
}