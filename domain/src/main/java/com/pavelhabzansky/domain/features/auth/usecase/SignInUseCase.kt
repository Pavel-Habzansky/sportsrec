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
        ) {
            if (it != null) {
                params.onError(it)
                return@signIn
            }

            params.onSignInSuccess()
        }
    }

    data class Params(
        val email: String,
        val password: String,
        val onSignInSuccess: () -> Unit,
        val onError: (Throwable) -> Unit
    )
}