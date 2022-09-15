package com.pavelhabzansky.domain.features.auth.service

import com.pavelhabzansky.domain.features.auth.model.User

interface AuthService {
    fun signIn(
        email: String,
        password: String,
        onComplete: (Throwable?) -> Unit
    )

    fun signUp(
        email: String,
        password: String,
        onComplete: (Throwable?) -> Unit
    )
}