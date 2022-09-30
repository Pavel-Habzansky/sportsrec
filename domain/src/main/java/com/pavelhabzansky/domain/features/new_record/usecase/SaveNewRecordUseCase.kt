package com.pavelhabzansky.domain.features.new_record.usecase

import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.model.StorageType
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import com.pavelhabzansky.domain.core.Result

class SaveNewRecordUseCase(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    suspend operator fun invoke(params: Params): Result<Boolean> {
        return try {
            sportsRecordsRepository.createSportsRecord(params.record)

            if (params.record.storage == StorageType.REMOTE) {
                // TODO Upload sports record
            }

            Result.Success(true)
        } catch (ex: Exception) {
            Result.Failure(ex)
        }
    }

    data class Params(
        val record: SportsRecord
    )
}