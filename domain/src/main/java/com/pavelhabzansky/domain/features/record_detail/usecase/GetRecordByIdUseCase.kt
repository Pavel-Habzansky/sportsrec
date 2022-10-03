package com.pavelhabzansky.domain.features.record_detail.usecase

import com.pavelhabzansky.domain.core.common.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository

class GetRecordByIdUseCase(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    suspend operator fun invoke(params: Params): SportsRecord {
        return sportsRecordsRepository.getSportRecordById(params.id)
    }

    data class Params(
        val id: String
    )
}