package com.pavelhabzansky.data.features.sports_records

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pavelhabzansky.data.features.sports_records.entity.PerformanceEntity
import com.pavelhabzansky.data.features.sports_records.entity.SportsRecordEntity
import com.pavelhabzansky.domain.features.sports_records.model.PerformanceRecord
import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.model.StorageType

fun SportsRecordEntity.toDomain(): SportsRecord {
    return SportsRecord(
        name = name,
        location = location,
        createTime = createTime,
        performanceRecord = performanceRecord.toDomain(),
        storage = StorageType.valueOf(storage)
    )
}

fun PerformanceEntity.toDomain(): PerformanceRecord {
    return when (this) {
        is PerformanceEntity.WeightliftingEntity -> PerformanceRecord.Weightlifting(
            weight = weight,
            sets = sets,
            repsPerSet = repsPerSet
        )
        is PerformanceEntity.SprintEntity -> PerformanceRecord.Sprint(
            distance = distance,
            time = time
        )
        is PerformanceEntity.RopeJumpEntity -> PerformanceRecord.RopeJump(
            jumps = jumps,
            time = time
        )
        is PerformanceEntity.CustomRecordEntity -> PerformanceRecord.Custom(
            performance = performance,
            time = time
        )
    }
}

fun List<SportsRecordEntity>.toDomain(): List<SportsRecord> {
    return map { it.toDomain() }
}

fun SportsRecord.toEntity(): SportsRecordEntity {
    return SportsRecordEntity(
        name = name,
        performanceRecord = performanceRecord.toEntity(),
        location = location,
        createTime = createTime,
        storage = storage.name,
        synced = false,
        owner = Firebase.auth.currentUser?.email ?: "UNKNOWN"
    )
}

fun PerformanceRecord.toEntity(): PerformanceEntity {
    return when (this) {
        is PerformanceRecord.Weightlifting -> PerformanceEntity.WeightliftingEntity(
            weight = weight,
            sets = sets,
            repsPerSet = repsPerSet
        )
        is PerformanceRecord.Sprint -> PerformanceEntity.SprintEntity(
            distance = distance,
            time = time
        )
        is PerformanceRecord.RopeJump -> PerformanceEntity.RopeJumpEntity(
            jumps = jumps,
            time = time
        )
        is PerformanceRecord.Custom -> PerformanceEntity.CustomRecordEntity(
            performance = performance,
            time = time
        )
    }
}
