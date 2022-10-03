package com.pavelhabzansky.domain.features.sports_records.usecase

import com.pavelhabzansky.domain.core.common.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import kotlinx.coroutines.flow.Flow

class GetSportsRecordsUseCase(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    operator fun invoke(): Flow<List<SportsRecord>> {
        return sportsRecordsRepository.getSportsRecordsFlow()
    }
}