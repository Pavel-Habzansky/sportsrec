package com.pavelhabzansky.domain.features.new_record.usecase

import com.pavelhabzansky.domain.core.common.SportsRecord

class IsSportsRecordValidUseCase {
    operator fun invoke(params: Params): Boolean {
        val record = params.record
        return record.performanceRecord.isValid() && record.name.isNotEmpty()
    }

    data class Params(
        val record: SportsRecord
    )
}