package com.pavelhabzansky.sportsrec.core.di

import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import com.pavelhabzansky.domain.features.sports_records.usecase.FetchSportsRecordsUseCase
import com.pavelhabzansky.domain.features.sports_records.usecase.GetSportsRecordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SportsRecordsModule {

    @Provides
    @Singleton
    fun provideGetSportsRecordsUseCase(
        sportsRecordsRepository: SportsRecordsRepository
    ): GetSportsRecordsUseCase {
        return GetSportsRecordsUseCase(
            sportsRecordsRepository = sportsRecordsRepository
        )
    }

    @Provides
    @Singleton
    fun provideFetchSportsRecordsUseCase(
        sportsRecordsRepository: SportsRecordsRepository
    ): FetchSportsRecordsUseCase {
        return FetchSportsRecordsUseCase(
            sportsRecordsRepository = sportsRecordsRepository
        )
    }

}