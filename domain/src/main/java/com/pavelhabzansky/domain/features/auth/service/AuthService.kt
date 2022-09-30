package com.pavelhabzansky.domain.features.auth.service

interface AuthService {
    fun signIn(
        email: String,
        password: String,
        onComplete: (String?, Throwable?) -> Unit
    )

    fun signUp(
        email: String,
        password: String,
        onComplete: (String?, Throwable?) -> Unit
    )
}