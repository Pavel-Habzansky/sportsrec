package com.pavelhabzansky.domain.features.auth.usecase

import com.pavelhabzansky.domain.features.auth.service.AuthService
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authService: AuthService
) {
    operator fun invoke(params: Params) {
        authService.signIn(
            email = params.email,
            password = params.password,
        ) { email, throwable ->
            if (throwable != null) {
                params.onError(throwable)
                return@signIn
            }

            params.onSignInSuccess(requireNotNull(email))
        }
    }

    data class Params(
        val email: String,
        val password: String,
        val onSignInSuccess: (String) -> Unit,
        val onError: (Throwable) -> Unit
    )
}