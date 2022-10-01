package com.pavelhabzansky.data.features.sports_records.api

data class SportsRecordDto(
    val id: Long? = null,
    val name: String,
    val performanceRecord: PerformanceRecordDto,
    val location: String?,
    val createTime: Long
)