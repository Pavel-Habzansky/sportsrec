package com.pavelhabzansky.data.features.sports_records.repository

import com.pavelhabzansky.data.features.sports_records.dao.SportsRecordsDao
import com.pavelhabzansky.data.features.sports_records.toDomain
import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SportsRecordsRepositoryImpl(
    private val sportsRecordsDao: SportsRecordsDao
) : SportsRecordsRepository {

    override fun getSportsRecordsFlow(): Flow<List<SportsRecord>> {
        return sportsRecordsDao.getSportsRecordsFlow()
            .map { it.toDomain() }
    }

}