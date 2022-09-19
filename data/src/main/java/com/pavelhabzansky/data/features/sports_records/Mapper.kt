package com.pavelhabzansky.data.features.sports_records

import com.pavelhabzansky.data.features.sports_records.entity.SportsRecordEntity
import com.pavelhabzansky.domain.features.sports_records.model.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.model.StorageType

fun SportsRecordEntity.toDomain(): SportsRecord {
    return SportsRecord(
        name = name,
        location = location,
        duration = duration,
        storage = StorageType.valueOf(storage),
        createTime = createTime
    )
}

fun List<SportsRecordEntity>.toDomain(): List<SportsRecord> {
    return map { it.toDomain() }
}

fun SportsRecord.toEntity(): SportsRecordEntity {
    return SportsRecordEntity(
        name = name,
        location = location,
        duration = duration,
        storage = storage.name,
        createTime = createTime
    )
}
