package com.pavelhabzansky.sportsrec.core.di

import com.pavelhabzansky.data.core.AppDatabase
import com.pavelhabzansky.data.features.auth.service.AuthServiceImpl
import com.pavelhabzansky.data.features.sports_records.api.RemoteApiService
import com.pavelhabzansky.data.features.sports_records.repository.SportsRecordsRepositoryImpl
import com.pavelhabzansky.domain.features.auth.service.AuthService
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthService(): AuthService {
        return AuthServiceImpl()
    }

    @Provides
    @Singleton
    fun provideSportsRecordsRepository(
        db: AppDatabase,
        remoteApi: RemoteApiService
    ): SportsRecordsRepository {
        return SportsRecordsRepositoryImpl(
            sportsRecordsDao = db.sportsRecordDao,
            remoteApi = remoteApi
        )
    }
}