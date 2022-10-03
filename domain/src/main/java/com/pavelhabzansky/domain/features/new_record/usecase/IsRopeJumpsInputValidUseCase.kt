package com.pavelhabzansky.domain.features.new_record.usecase

class IsRopeJumpsInputValidUseCase {

    operator fun invoke(params: Params): Boolean {
        if (params.jumpsInput.isEmpty()) return true
        return try {
            params.jumpsInput.toInt()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }

    data class Params(
        val jumpsInput: String
    )
}