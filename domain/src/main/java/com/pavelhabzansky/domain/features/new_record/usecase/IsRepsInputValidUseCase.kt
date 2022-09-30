package com.pavelhabzansky.domain.features.new_record.usecase

class IsRepsInputValidUseCase {
    operator fun invoke(params: Params): Boolean {
        if (params.repsInput.isEmpty()) return true
        return try {
            val intValue = params.repsInput.toInt()
            intValue in 1..50
        } catch (ex: NumberFormatException) {
            false
        }
    }

    data class Params(
        val repsInput: String
    )
}