package com.pavelhabzansky.data.features.sports_records.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pavelhabzansky.data.features.sports_records.api.RemoteApiService
import com.pavelhabzansky.data.features.sports_records.dao.SportsRecordsDao
import com.pavelhabzansky.data.features.sports_records.toDataTransfer
import com.pavelhabzansky.data.features.sports_records.toDomain
import com.pavelhabzansky.domain.core.Result
import com.pavelhabzansky.data.features.sports_records.toEntity
import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

class SportsRecordsRepositoryImpl(
    private val sportsRecordsDao: SportsRecordsDao,
    private val remoteApi: RemoteApiService
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

    override suspend fun uploadSportsRecord(record: SportsRecord) {
        withContext(Dispatchers.IO) {
            val uid = Firebase.auth.currentUser?.uid
            if (uid != null) {
                val dataTransfer = record.toDataTransfer()
                val response = remoteApi.postRecord(
                    uid = uid,
                    key = record.id,
                    body = dataTransfer
                ).await()
                Timber.i(response.string())
            }
        }
    }

    override suspend fun clearOldData(newUid: String) {
        sportsRecordsDao.clearRecords(newUid)
    }

    override suspend fun fetchRemoteData(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val uid = Firebase.auth.currentUser?.uid
            if (uid != null) {
                val response = remoteApi.getRecords(uid).await()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { dataMap ->
                        val entities = dataMap.map {
                            it.value.toEntity(ownerUid = uid, key = it.key)
                        }
                        sportsRecordsDao.insert(entities)
                        Result.Success(Unit)
                    } ?: run {
                        Result.Failure(
                            throwable = IllegalStateException("Response has no body")
                        )
                    }
                } else {
                    Result.Failure(
                        throwable = IllegalStateException("Response is not successful! code = ${response.code()}")
                    )
                }
            } else {
                Result.Failure(
                    throwable = IllegalStateException("User does not have uid!")
                )
            }
        }
    }

}