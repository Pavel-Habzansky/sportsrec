package com.pavelhabzansky.sportsrec.core.di

import com.pavelhabzansky.domain.features.record_detail.usecase.GetRecordByIdUseCase
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import com.pavelhabzansky.domain.features.sports_records.usecase.*
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

    @Provides
    @Singleton
    fun provideFilterRecordsUseCase(): FilterRecordsUseCase {
        return FilterRecordsUseCase()
    }

    @Provides
    @Singleton
    fun provideUploadLocalRecordsUseCase(
        sportsRecordsRepository: SportsRecordsRepository
    ): UploadLocalRecordsUseCase {
        return UploadLocalRecordsUseCase(sportsRecordsRepository)
    }

    @Provides
    @Singleton
    fun provideGetRecordByIdUseCase(
        sportsRecordsRepository: SportsRecordsRepository
    ): GetRecordByIdUseCase {
        return GetRecordByIdUseCase(
            sportsRecordsRepository = sportsRecordsRepository
        )
    }

    @Provides
    @Singleton
    fun provideDeleteRecordUseCase(
        sportsRecordsRepository: SportsRecordsRepository
    ): DeleteRecordUseCase {
        return DeleteRecordUseCase(
            sportsRecordsRepository = sportsRecordsRepository
        )
    }

}