package com.pavelhabzansky.domain.features.sports_records.usecase

import com.pavelhabzansky.domain.features.sports_records.model.UploadLocalsResult
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository

class UploadLocalRecordsUseCase(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    suspend operator fun invoke(): UploadLocalsResult {
        return sportsRecordsRepository.uploadLocalRecords()
    }
}