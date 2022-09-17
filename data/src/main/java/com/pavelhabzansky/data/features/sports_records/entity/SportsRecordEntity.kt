package com.pavelhabzansky.data.features.sports_records.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class SportsRecordEntity(
    @PrimaryKey
    val id: Long? = null,
    val name: String,
    val location: String,
    val duration: Long,
    val storage: String,
    val createTime: LocalDateTime
)