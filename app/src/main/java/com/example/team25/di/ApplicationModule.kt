package com.example.team25.di

import android.content.Context
import com.example.team25.HospitalInitializer
import com.example.team25.dao.HospitalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideHospitalInitializer(
        @ApplicationContext context: Context,
        hospitalDao: HospitalDao,
        @IoDispatcher dispatcher: CoroutineDispatcher

    ): HospitalInitializer {
        return HospitalInitializer(context, hospitalDao, dispatcher)
    }
}
