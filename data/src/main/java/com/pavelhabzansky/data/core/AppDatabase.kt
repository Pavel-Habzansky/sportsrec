package com.pavelhabzansky.data.core

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pavelhabzansky.data.features.sports_records.dao.SportsRecordsDao
import com.pavelhabzansky.data.features.sports_records.entity.SportsRecordEntity
import java.time.LocalDate

@Database(
    entities = [
        SportsRecordEntity::class
    ],
    version = 1
)
@TypeConverters(
    value = [
        LocalDateTimeConverter::class,
        SportsTypeConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract val sportsRecordDao: SportsRecordsDao

}
