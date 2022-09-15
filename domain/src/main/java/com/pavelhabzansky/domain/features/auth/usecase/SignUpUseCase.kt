package com.pavelhabzansky.domain.features.auth.usecase

import com.pavelhabzansky.domain.features.auth.service.AuthService
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(params: Params) {
        authService.signUp(
            email = params.email,
            password = params.password,
        ) {
            if (it != null) {
                params.onError(it)
                return@signUp
            }

            params.onSuccess()
        }
    }

    data class Params(
        val email: String,
        val password: String,
        val onSuccess: () -> Unit,
        val onError: (Throwable) -> Unit
    )
}