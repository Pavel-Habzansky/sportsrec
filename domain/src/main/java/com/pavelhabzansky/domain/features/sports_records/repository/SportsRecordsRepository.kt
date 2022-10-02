package com.pavelhabzansky.domain.features.sports_records.repository

import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.domain.core.Result
import com.pavelhabzansky.domain.features.sports_records.model.FetchRemotesResult
import com.pavelhabzansky.domain.features.sports_records.model.UploadLocalsResult
import kotlinx.coroutines.flow.Flow

interface SportsRecordsRepository {

    fun getSportsRecordsFlow(): Flow<List<SportsRecord>>
    suspend fun createSportsRecord(record: SportsRecord)
    suspend fun uploadSportsRecord(record: SportsRecord)
    suspend fun clearOldData(newUid: String)
    suspend fun fetchRemoteData(): FetchRemotesResult
    suspend fun uploadLocalRecords(): UploadLocalsResult

}