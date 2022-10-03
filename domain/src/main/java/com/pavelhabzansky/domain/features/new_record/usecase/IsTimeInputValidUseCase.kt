package com.pavelhabzansky.domain.features.new_record.usecase

class IsTimeInputValidUseCase {

    operator fun invoke(params: Params): Boolean {
        if (params.timeInput.isEmpty()) return true
        return try {
            params.timeInput.toFloat()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }

    data class Params(
        val timeInput: String
    )
}