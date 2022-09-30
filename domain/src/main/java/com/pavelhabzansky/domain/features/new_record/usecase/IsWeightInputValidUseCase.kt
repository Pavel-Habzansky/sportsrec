package com.pavelhabzansky.domain.features.new_record.usecase

class IsWeightInputValidUseCase {

    operator fun invoke(params: Params): Boolean {
        if (params.weight.isEmpty()) return true
        return try {
            val intValue = params.weight.toInt()
            intValue in 1..1000
        } catch (ex: NumberFormatException) {
            false
        }
    }

    data class Params(
        val weight: String
    )
}