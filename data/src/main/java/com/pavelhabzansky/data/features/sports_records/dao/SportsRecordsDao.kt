package com.pavelhabzansky.data.features.sports_records.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pavelhabzansky.data.features.sports_records.entity.SportsRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SportsRecordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SportsRecordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<SportsRecordEntity>)

    @Query("SELECT * FROM SportsRecordEntity")
    fun getSportsRecordsFlow(): Flow<List<SportsRecordEntity>>

    @Query("SELECT * FROM SportsRecordEntity WHERE storage = :storage")
    fun getSportsRecordsFlowByStorage(storage: String): Flow<List<SportsRecordEntity>>

    @Query("DELETE FROM SportsRecordEntity WHERE owner != :newEmail")
    fun clearRecords(newEmail: String)
}