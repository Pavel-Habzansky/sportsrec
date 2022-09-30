package com.pavelhabzansky.data.features.sports_records.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class SportsRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val performanceRecord: PerformanceEntity,
    val location: String?,
    val synced: Boolean,
    val storage: String,
    val createTime: LocalDateTime,
    val owner: String
)