package com.pavelhabzansky.domain.features.auth.usecase

import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository

class ClearOldUsersDataUseCase(
    private val sportsRecordsRepository: SportsRecordsRepository
) {

    suspend operator fun invoke(params: Params) {
        sportsRecordsRepository.clearOldData(params.newEmail)
    }

    data class Params(
        val newEmail: String
    )
}