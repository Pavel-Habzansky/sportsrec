package com.pavelhabzansky.sportsrec.core.di

import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import com.pavelhabzansky.domain.features.sports_records.usecase.GetSportsRecordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SportsRecordsModule {

    @Provides
    fun provideGetSportsRecordsUseCase(
        sportsRecordsRepository: SportsRecordsRepository
    ): GetSportsRecordsUseCase {
        return GetSportsRecordsUseCase(
            sportsRecordsRepository = sportsRecordsRepository
        )
    }

}