package com.pavelhabzansky.sportsrec.core.di

import com.pavelhabzansky.data.features.auth.AuthServiceImpl
import com.pavelhabzansky.domain.features.auth.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthService(): AuthService {
        return AuthServiceImpl()
    }
}