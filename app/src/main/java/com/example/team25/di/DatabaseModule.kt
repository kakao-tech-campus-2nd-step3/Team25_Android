package com.example.team25.di

import android.content.Context
import androidx.room.Room
import com.example.team25.data.dao.HospitalDao
import com.example.team25.data.database.HospitalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideHospitalDatabase(
        @ApplicationContext context: Context,
    ): HospitalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HospitalDatabase::class.java,
            "hospital_database",
        ).build()
    }

    @Provides
    fun provideHospitalDao(database: HospitalDatabase): HospitalDao {
        return database.hospitalDao()
    }
}
