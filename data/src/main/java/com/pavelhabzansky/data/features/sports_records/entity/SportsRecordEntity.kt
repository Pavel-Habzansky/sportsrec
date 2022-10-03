package com.pavelhabzansky.data.features.sports_records.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SportsRecordEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val performanceRecord: PerformanceEntity,
    val location: String?,
    val synced: Boolean,
    val storage: String,
    val createTime: Long,
    val owner: String
)