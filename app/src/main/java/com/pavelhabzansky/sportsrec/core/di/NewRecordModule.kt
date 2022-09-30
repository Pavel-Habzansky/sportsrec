package com.pavelhabzansky.sportsrec.core.di

import com.pavelhabzansky.domain.features.new_record.usecase.*
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewRecordModule {

    @Provides
    @Singleton
    fun provideIsWeightInputValidUseCase(): IsWeightInputValidUseCase {
        return IsWeightInputValidUseCase()
    }

    @Provides
    @Singleton
    fun provideIsRepsInputValidUseCase(): IsRepsInputValidUseCase {
        return IsRepsInputValidUseCase()
    }

    @Provides
    @Singleton
    fun provideIsSprintDistanceInputValidUseCase(): IsSprintDistanceInputValidUseCase {
        return IsSprintDistanceInputValidUseCase()
    }

    @Provides
    @Singleton
    fun provideIsSprintTimeInputValidUseCase(): IsTimeInputValidUseCase {
        return IsTimeInputValidUseCase()
    }

    @Provides
    @Singleton
    fun provideIsRopeJumpsInputValidUseCase(): IsRopeJumpsInputValidUseCase {
        return IsRopeJumpsInputValidUseCase()
    }

    @Provides
    @Singleton
    fun provideIsSportsRecordValidUseCase(): IsSportsRecordValidUseCase {
        return IsSportsRecordValidUseCase()
    }

    @Provides
    @Singleton
    fun provideSaveNewRecordUseCase(
        recordsRepository: SportsRecordsRepository
    ): SaveNewRecordUseCase {
        return SaveNewRecordUseCase(sportsRecordsRepository = recordsRepository)
    }
}