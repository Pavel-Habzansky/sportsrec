package com.pavelhabzansky.data.features.sports_records.repository

import com.pavelhabzansky.data.features.sports_records.dao.SportsRecordsDao
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository

class SportsRecordsRepositoryImpl(
    private val sportsRecordsDao: SportsRecordsDao
) : SportsRecordsRepository {
}