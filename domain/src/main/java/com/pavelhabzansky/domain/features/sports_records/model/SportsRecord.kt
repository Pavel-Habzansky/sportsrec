package com.pavelhabzansky.domain.features.sports_records.model

data class SportsRecord(
    val id: String,
    val name: String,
    val performanceRecord: PerformanceRecord,
    val location: String?,
    val storage: StorageType,
    val createTime: Long
)
