package com.pavelhabzansky.data.features.sports_records.entity

import com.squareup.moshi.Json

sealed class PerformanceEntity(@Json(name = "type") val type: PerformanceEntityType) {
    data class WeightliftingEntity(
        val weight: Int,
        val sets: Int,
        val repsPerSet: Map<Int, Int>
    ) : PerformanceEntity(PerformanceEntityType.WEIGHTLIFTING)

    data class SprintEntity(
        val distance: Int,
        val time: Float
    ) : PerformanceEntity(PerformanceEntityType.SPRINT)

    data class RopeJumpEntity(
        val jumps: Int,
        val time: Float
    ) : PerformanceEntity(PerformanceEntityType.ROPE_JUMP)

    data class CustomRecordEntity(
        val performance: String,
        val time: Float
    ) : PerformanceEntity(PerformanceEntityType.CUSTOM)
}