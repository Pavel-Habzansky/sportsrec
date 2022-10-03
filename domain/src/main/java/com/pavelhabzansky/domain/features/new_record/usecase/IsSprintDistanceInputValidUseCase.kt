package com.pavelhabzansky.domain.features.new_record.usecase

class IsSprintDistanceInputValidUseCase {

    operator fun invoke(params: Params): Boolean {
        if (params.distanceInput.isEmpty()) return true
        return try {
            val intValue = params.distanceInput.toInt()
            intValue <= 500
        } catch (ex: NumberFormatException) {
            false
        }
    }

    data class Params(
        val distanceInput: String
    )
}