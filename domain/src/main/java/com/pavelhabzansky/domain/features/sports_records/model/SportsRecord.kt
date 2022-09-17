package com.pavelhabzansky.domain.features.sports_records.model

import java.time.LocalDateTime

data class SportsRecord(
    val name: String,
    val location: String,
    val duration: Long,
    val storage: StorageType,
    val createTime: LocalDateTime
)
