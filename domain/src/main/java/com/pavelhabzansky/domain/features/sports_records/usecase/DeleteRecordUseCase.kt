package com.pavelhabzansky.domain.features.sports_records.usecase

import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import com.pavelhabzansky.domain.core.Result

class DeleteRecordUseCase(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    suspend operator fun invoke(params: Params): Result<Boolean> {
        return sportsRecordsRepository.deleteSportsRecord(params.id)
    }

    data class Params(
        val id: String
    )

}