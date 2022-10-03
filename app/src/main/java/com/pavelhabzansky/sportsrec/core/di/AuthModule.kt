package com.pavelhabzansky.sportsrec.core.di

import com.pavelhabzansky.domain.features.auth.service.AuthService
import com.pavelhabzansky.domain.features.auth.usecase.*
import com.pavelhabzansky.domain.features.sports_records.repository.SportsRecordsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun provideSignInUseCase(
        authService: AuthService,
    ): SignInUseCase {
        return SignInUseCase(
            authService = authService
        )
    }

    @Provides
    fun provideSignUpUseCase(
        authService: AuthService
    ): SignUpUseCase {
        return SignUpUseCase(authService)
    }

    @Provides
    fun provideClearOldUsersDataUseCase(
        sportsRecordsRepository: SportsRecordsRepository
    ): ClearOldUsersDataUseCase {
        return ClearOldUsersDataUseCase(
            sportsRecordsRepository = sportsRecordsRepository
        )
    }

    @Provides
    fun provideValidateAuthInputUseCase(): ValidateAuthInputUseCase {
        return ValidateAuthInputUseCase()
    }
}