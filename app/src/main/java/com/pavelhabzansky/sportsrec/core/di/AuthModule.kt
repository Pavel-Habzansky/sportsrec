package com.pavelhabzansky.sportsrec.core.di

import com.pavelhabzansky.domain.features.auth.service.AuthService
import com.pavelhabzansky.domain.features.auth.usecase.SignInUseCase
import com.pavelhabzansky.domain.features.auth.usecase.SignUpUseCase
import com.pavelhabzansky.domain.features.auth.usecase.ValidateAuthInputUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun provideSignInUseCase(
        authService: AuthService
    ): SignInUseCase {
        return SignInUseCase(authService)
    }

    @Provides
    fun provideSignUpUseCase(
        authService: AuthService
    ): SignUpUseCase {
        return SignUpUseCase(authService)
    }

    @Provides
    fun provideValidateAuthInputUseCase(): ValidateAuthInputUseCase {
        return ValidateAuthInputUseCase()
    }
}