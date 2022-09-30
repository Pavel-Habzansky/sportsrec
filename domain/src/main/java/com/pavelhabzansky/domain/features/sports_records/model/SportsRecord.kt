package com.pavelhabzansky.domain.features.sports_records.model

import java.time.LocalDateTime

data class SportsRecord(
    val name: String,
    val performanceRecord: PerformanceRecord,
    val location: String?,
    val storage: StorageType,
    val createTime: LocalDateTime
)
