package com.pavelhabzansky.sportsrec.features.record_list

import com.pavelhabzansky.domain.core.common.SportsRecord
import com.pavelhabzansky.domain.core.common.StorageType
import com.pavelhabzansky.domain.features.sports_records.model.PerformanceRecord
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.features.record_list.model.RecordListItem
import com.pavelhabzansky.sportsrec.features.record_list.model.StorageListType
import java.text.SimpleDateFormat
import java.util.*

val DATE_FORMAT = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")

fun List<SportsRecord>.toListItems(): List<RecordListItem> {
    return map { it.toListItem() }
}

fun StorageType.mapToListType(): StorageListType {
    return when (this) {
        StorageType.LOCAL -> StorageListType.LOCAL
        StorageType.REMOTE -> StorageListType.REMOTE
    }
}

fun Long.mapToListDate(): String {
    return DATE_FORMAT.format(Date(this))
}

fun SportsRecord.toListItem(): RecordListItem {
    return when (performanceRecord) {
        is PerformanceRecord.Weightlifting -> {
            val performance = performanceRecord as PerformanceRecord.Weightlifting
            RecordListItem.WeightliftingListItem(
                id = id,
                name = name,
                createTime = createTime.mapToListDate(),
                image = R.drawable.weightlifter,
                storageType = storage.mapToListType(),
                weight = performance.weight,
                sets = performance.sets,
                totalWeight = performance.weight * performance.repsPerSet.values.sum(),
            )
        }
        is PerformanceRecord.Sprint -> {
            val performance = performanceRecord as PerformanceRecord.Sprint
            RecordListItem.SprintListItem(
                id = id,
                name = name,
                createTime = createTime.mapToListDate(),
                image = R.drawable.sprint,
                storageType = storage.mapToListType(),
                distance = performance.distance,
                time = performance.time
            )
        }
        is PerformanceRecord.RopeJump -> {
            val performance = performanceRecord as PerformanceRecord.RopeJump
            RecordListItem.RopeJumpListItem(
                id = id,
                name = name,
                image = R.drawable.jump_rope,
                storageType = storage.mapToListType(),
                createTime = createTime.mapToListDate(),
                jumps = performance.jumps,
                time = performance.time
            )
        }
        is PerformanceRecord.Custom -> {
            val performance = performanceRecord as PerformanceRecord.Custom
            RecordListItem.CustomListItem(
                id = id,
                name = name,
                createTime = createTime.mapToListDate(),
                image = R.drawable.speedometer,
                storageType = storage.mapToListType(),
                performance = performance.performance,
                time = performance.time
            )
        }
    }
}