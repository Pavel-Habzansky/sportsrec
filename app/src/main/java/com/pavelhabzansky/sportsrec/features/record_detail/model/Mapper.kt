package com.pavelhabzansky.sportsrec.features.record_detail.model

import com.pavelhabzansky.domain.core.common.SportsRecord
import com.pavelhabzansky.domain.features.sports_records.model.PerformanceRecord
import com.pavelhabzansky.sportsrec.core.utils.toLatLng
import com.pavelhabzansky.sportsrec.features.new_record.model.RecordType

fun SportsRecord.toDetailScreen(): RecordDetail {
    return when (performanceRecord) {
        is PerformanceRecord.Weightlifting -> {
            val performance = performanceRecord as PerformanceRecord.Weightlifting
            RecordDetail.Weightlifting(
                id = id,
                name = name,
                type = RecordType.WEIGHTLIFTING,
                location = location.toLatLng(),
                weight = performance.weight.toString(),
                sets = performance.sets,
                repsPerSet = performance.repsPerSet.mapValues { it.toString() }
            )
        }
        is PerformanceRecord.Sprint -> {
            val performance = performanceRecord as PerformanceRecord.Sprint
            RecordDetail.Sprint(
                id = id,
                name = name,
                type = RecordType.SPRINT,
                location = location.toLatLng(),
                distance = performance.distance.toString(),
                time = performance.time.toString()
            )
        }
        is PerformanceRecord.RopeJump -> {
            val performance = performanceRecord as PerformanceRecord.RopeJump
            RecordDetail.RopeJump(
                id = id,
                name = name,
                type = RecordType.ROPE_JUMP,
                location = location.toLatLng(),
                jumps = performance.jumps.toString(),
                time = performance.time.toString()
            )
        }
        is PerformanceRecord.Custom -> {
            val performance = performanceRecord as PerformanceRecord.Custom
            RecordDetail.Custom(
                id = id,
                name = name,
                type = RecordType.CUSTOM,
                location = location.toLatLng(),
                performance = performance.performance,
                time = performance.time.toString()
            )
        }
    }
}