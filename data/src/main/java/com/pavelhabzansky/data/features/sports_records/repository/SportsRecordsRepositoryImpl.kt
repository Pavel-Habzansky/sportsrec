package com.pavelhabzansky.data.features.sports_records.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pavelhabzansky.data.features.sports_records.api.RemoteApiService
import com.pavelhabzansky.data.features.sports_records.dao.SportsRecordsDao
import com.pavelhabzansky.data.features.sports_records.toDataTransfer
import com.pavelhabzansky.data.features.sports_records.toDomain
import com.pavelhabzansky.domain.core.Result
import com.pavelhabzansky.data.features.sports_records.toEntity
import com.pavelhabzansky.domain.features.sports_records.model.FetchRemotesResult
import com.pavelhabzansky.domain.core.common.SportsRecord
import com.pavelhabzansky.domain.core.common.StorageType
import com.pavelhabzansky.domain.features.sports_records.model.UploadLocalsResult
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
                val response = remoteApi.putRecord(
                    uid = uid,
                    key = record.id,
                    body = dataTransfer
                ).await()
                Timber.i(response.body().toString())
            }
        }
    }

    override suspend fun clearOldData(newUid: String) {
        sportsRecordsDao.clearRecords(newUid)
    }

    override suspend fun fetchRemoteData(): FetchRemotesResult {
        return withContext(Dispatchers.IO) {
            try {
                val uid = Firebase.auth.currentUser?.uid
                if (uid != null) {
                    val response = remoteApi.getRecords(uid).await()
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let { dataMap ->
                            val entities = dataMap.map {
                                it.value.toEntity(ownerUid = uid, key = it.key)
                            }
                            val current = sportsRecordsDao.getAllRecords()
                                .map { it.id }
                            sportsRecordsDao.insert(entities)

                            val diff = entities.count { !current.contains(it.id) }
                            if (diff == 0) {
                                FetchRemotesResult.NoNewData
                            } else {
                                FetchRemotesResult.DownloadSuccess(diff)
                            }
                        } ?: run {
                            FetchRemotesResult.DownloadFailure(IllegalStateException("Response body is empty!"))
                        }
                    } else {
                        FetchRemotesResult.DownloadFailure(IllegalStateException("API call was unsuccessful! code=${response.code()}"))
                    }
                } else {
                    FetchRemotesResult.DownloadFailure(IllegalStateException("User has no UID!"))
                }
            } catch (ex: Exception) {
                Timber.w(ex, "Download of remote records was unsuccessful")
                FetchRemotesResult.DownloadFailure(ex)
            }

        }
    }

    override suspend fun uploadLocalRecords(): UploadLocalsResult {
        return withContext(Dispatchers.IO) {
            try {
                var successCount = 0
                val uid = Firebase.auth.currentUser?.uid
                if (uid == null) {
                    return@withContext UploadLocalsResult.UploadFailure()
                }
                val localRecords = sportsRecordsDao.getLocalRecords()
                if (localRecords.isEmpty()) {
                    return@withContext UploadLocalsResult.NoDataToUpload
                }
                val dataTransfers = localRecords.toDataTransfer()
                dataTransfers.forEach { record ->
                    val response = remoteApi.putRecord(
                        uid = uid,
                        key = requireNotNull(record.key),
                        body = record
                    ).await()

                    if (response.isSuccessful) {
                        sportsRecordsDao.updateStorage(
                            id = record.key,
                            storage = StorageType.REMOTE.name
                        )
                        successCount++
                    }
                }
                UploadLocalsResult.UploadSuccessful(successCount)
            } catch (ex: Exception) {
                Timber.w(ex, "Upload of local records was unsuccessful")
                UploadLocalsResult.UploadFailure(ex)
            }
        }
    }

    override suspend fun getSportRecordById(id: String): SportsRecord {
        return withContext(Dispatchers.IO) {
            sportsRecordsDao.getRecordById(id).toDomain()
        }
    }

    override suspend fun deleteSportsRecord(id: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val record = sportsRecordsDao.getRecordById(id)
                if (record.storage == StorageType.REMOTE.name) {
                    val uid = Firebase.auth.uid
                    if (uid != null) {
                        val response = remoteApi.deleteRecord(uid, id).await()
                        if (response.isSuccessful) {
                            Timber.i("Remote record deletion response code: ${response.code()}")
                        } else {
                            throw IllegalStateException("Couldn't delete remote record due to HTTP error")
                        }
                    } else {
                        throw IllegalStateException("Couldn't delete remote record due to empty UID")
                    }
                }

                sportsRecordsDao.deleteSportsRecordById(id)

                Result.Success(true)
            } catch (ex: Exception) {
                Timber.w(ex, "Error occurred during record deleetion")
                Result.Failure(ex)
            }
        }
    }
}