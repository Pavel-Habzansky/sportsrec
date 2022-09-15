package com.pavelhabzansky.data.core

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

}

@Entity
data class UserEntity(
    @PrimaryKey
    val id: Int
)