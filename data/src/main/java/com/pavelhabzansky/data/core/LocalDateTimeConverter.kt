package com.pavelhabzansky.data.core

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): String {
        return Gson().toJson(localDateTime)
    }

    @TypeConverter
    fun toLocalDateTime(json: String): LocalDateTime {
        return Gson().fromJson(json, LocalDateTime::class.java)
    }
}