package com.pavelhabzansky.domain.features.sports_records.model

sealed class PerformanceRecord {
    abstract fun isValid(): Boolean

    data class Weightlifting(
        val weight: Int,
        val sets: Int,
        val repsPerSet: Map<Int, Int>
    ) : PerformanceRecord() {
        override fun isValid(): Boolean {
            return weight > 0 && sets > 0 && repsPerSet.size == sets && repsPerSet.all { it.value > 0 }
        }
    }

    data class Sprint(
        val distance: Int,
        val time: Float
    ) : PerformanceRecord() {
        override fun isValid(): Boolean {
            return distance > 0 && time > 0f
        }
    }

    data class RopeJump(
        val jumps: Int,
        val time: Float
    ) : PerformanceRecord() {
        override fun isValid(): Boolean {
            return jumps > 0 && time > 0f
        }
    }

    data class Custom(
        val performance: String,
        val time: Float
    ) : PerformanceRecord() {
        override fun isValid(): Boolean {
            return performance.isNotEmpty() && time > 0f
        }
    }
}