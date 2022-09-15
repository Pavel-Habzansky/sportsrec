package com.pavelhabzansky.sportsrec.core.di

import com.pavelhabzansky.data.features.auth.service.AuthServiceImpl
import com.pavelhabzansky.domain.features.auth.service.AuthService
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
}