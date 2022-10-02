package com.pavelhabzansky.domain.features.sports_records.usecase

import com.pavelhabzansky.domain.features.sports_records.model.FilterOptions
import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.model.StorageType

class FilterRecordsUseCase {
    operator fun invoke(params: Params): List<SportsRecord> {
        return params.items.filter {
            (it.storage == StorageType.REMOTE && params.filterOptions.remote)
                    || (it.storage == StorageType.LOCAL && params.filterOptions.local)
        }
    }

    data class Params(
        val items: List<SportsRecord>,
        val filterOptions: FilterOptions,
    )
}