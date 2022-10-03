package com.pavelhabzansky.sportsrec.core.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pavelhabzansky.data.core.AppDatabase
import com.pavelhabzansky.data.core.AppDatabase.Companion.DB_NAME
import com.pavelhabzansky.data.core.BASE_URL
import com.pavelhabzansky.data.features.sports_records.api.PerformanceRecordDto
import com.pavelhabzansky.data.features.sports_records.api.RemoteApiService
import com.pavelhabzansky.data.features.sports_records.common.PerformanceType
import com.pavelhabzansky.sportsrec.core.preferences.FilterPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(PerformanceRecordDto::class.java, "type")
                    .withSubtype(
                        PerformanceRecordDto.Weightlifting::class.java,
                        PerformanceType.WEIGHTLIFTING.name
                    )
                    .withSubtype(
                        PerformanceRecordDto.Sprint::class.java,
                        PerformanceType.SPRINT.name
                    )
                    .withSubtype(
                        PerformanceRecordDto.RopeJump::class.java,
                        PerformanceType.ROPE_JUMP.name
                    )
                    .withSubtype(
                        PerformanceRecordDto.CustomRecord::class.java,
                        PerformanceType.CUSTOM.name
                    )
            )
            .add(KotlinJsonAdapterFactory())
            .build()


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteDBApi(retrofit: Retrofit): RemoteApiService {
        return retrofit.create(RemoteApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFilterPreferences(
        @ApplicationContext context: Context
    ): FilterPreferences {
        return FilterPreferences(context)
    }

}