package com.pavelhabzansky.domain.features.auth.service

interface AuthService {
    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    )

    fun signUp(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    )
}