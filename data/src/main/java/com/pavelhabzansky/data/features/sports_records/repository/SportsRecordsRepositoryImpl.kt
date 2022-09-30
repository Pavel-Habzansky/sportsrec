package com.pavelhabzansky.data.features.sports_records.repository

import com.pavelhabzansky.data.features.sports_records.dao.SportsRecordsDao
import com.pavelhabzansky.data.features.sports_records.toDomain
import com.pavelhabzansky.data.features.sports_records.toEntity
import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SportsRecordsRepositoryImpl(
    private val sportsRecordsDao: SportsRecordsDao
) : SportsRecordsRepository {

    override fun getSportsRecordsFlow(): Flow<List<SportsRecord>> {
        return sportsRecordsDao.getSportsRecordsFlow()
            .map { it.toDomain() }
    }

    override suspend fun createSportsRecord(record: SportsRecord) {
        withContext(Dispatchers.IO) {
            val sportsRecordEntity = record.toEntity()
            sportsRecordsDao.insert(sportsRecordEntity)
        }
    }

    override suspend fun clearOldData(newEmail: String) {
        sportsRecordsDao.clearRecords(newEmail)
    }

}