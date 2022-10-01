package com.pavelhabzansky.data.core

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pavelhabzansky.data.features.sports_records.common.PerformanceType
import com.pavelhabzansky.data.features.sports_records.entity.PerformanceEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PerformanceEntityConverter {

    @TypeConverter
    fun fromString(json: String): PerformanceEntity {
        val moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(PerformanceEntity::class.java, "type")
                    .withSubtype(
                        PerformanceEntity.WeightliftingEntity::class.java,
                        PerformanceType.WEIGHTLIFTING.name
                    )
                    .withSubtype(
                        PerformanceEntity.SprintEntity::class.java,
                        PerformanceType.SPRINT.name
                    )
                    .withSubtype(
                        PerformanceEntity.RopeJumpEntity::class.java,
                        PerformanceType.ROPE_JUMP.name
                    )
                    .withSubtype(
                        PerformanceEntity.CustomRecordEntity::class.java,
                        PerformanceType.CUSTOM.name
                    )
            )
            .add(KotlinJsonAdapterFactory())
            .build()

        val adapter = moshi.adapter(PerformanceEntity::class.java)
        return adapter.fromJson(json) as PerformanceEntity
    }

    @TypeConverter
    fun fromPerformanceEntity(entity: PerformanceEntity): String {
        return Gson().toJson(entity)
    }
}