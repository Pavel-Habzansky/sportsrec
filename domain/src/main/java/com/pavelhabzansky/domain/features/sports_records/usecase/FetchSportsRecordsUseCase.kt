package com.pavelhabzansky.domain.features.sports_records.usecase

import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository

class FetchSportsRecordsUseCase(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    suspend operator fun invoke() {
        sportsRecordsRepository.fetchRemoteData()
    }
}