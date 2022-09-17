package com.pavelhabzansky.data.core

import androidx.room.TypeConverter
import com.pavelhabzansky.data.features.sports_records.entity.SportsTypeEntity

class SportsTypeConverter {
    @TypeConverter
    fun fromString(text: String): SportsTypeEntity {
        return SportsTypeEntity.valueOf(text)
    }

    @TypeConverter
    fun fromSportsType(type: SportsTypeEntity): String {
        return type.name
    }
}