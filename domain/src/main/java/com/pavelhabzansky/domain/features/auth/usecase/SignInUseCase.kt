package com.pavelhabzansky.domain.features.auth.usecase

import com.pavelhabzansky.domain.features.auth.service.AuthService
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(params: Params) {
        authService.signIn(
            email = params.email,
            password = params.password,
            onSuccess = params.onSuccess,
            onError = params.onError
        )
    }

    data class Params(
        val email: String,
        val password: String,
        val onSuccess: () -> Unit,
        val onError: (Throwable) -> Unit
    )
}