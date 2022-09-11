package com.pavelhabzansky.domain.features.auth.usecase

import com.pavelhabzansky.domain.matchesEmail

class ValidateAuthInputUseCase {
    operator fun invoke(params: Params): Result {
        val email = params.email
        val password = params.password
        return when {
            email.isBlank() && password.isBlank() -> Result.EMAIL_AND_PASSWORD_EMPTY
            email.isBlank() -> Result.EMAIL_EMPTY
            password.isBlank() -> Result.PASSWORD_EMPTY
            !email.matchesEmail() -> Result.EMAIL_INVALID
            password.length < 8 -> Result.PASSWORD_SHORT
            else -> Result.SUCCESS
        }
    }

    data class Params(
        val email: String,
        val password: String
    )

    enum class Result {
        EMAIL_AND_PASSWORD_EMPTY,
        EMAIL_EMPTY,
        EMAIL_INVALID,

        PASSWORD_EMPTY,
        PASSWORD_SHORT,

        SUCCESS
    }
}