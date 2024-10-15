package com.example.team25.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.room.Room
import com.example.team25.TokensProto.Tokens
import com.example.team25.data.dao.HospitalDao
import com.example.team25.data.dao.ManagerDao
import com.example.team25.data.dao.ReservationDao
import com.example.team25.data.database.AppDatabase
import com.example.team25.data.database.TokenSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database",
        ).build()
    }

    @Provides
    fun provideHospitalDao(database: AppDatabase): HospitalDao {
        return database.hospitalDao()
    }

    @Provides
    fun provideManagerDao(database: AppDatabase): ManagerDao {
        return database.managerDao()
    }

    @Provides
    fun provideReservationDao(database: AppDatabase): ReservationDao {
        return database.reservationDao()
    }

    private val Context.tokenDataStore: DataStore<Tokens> by dataStore(
        fileName = "tokens.pb",
        serializer = TokenSerializer
    )

    @Provides
    @Singleton
    @TokenDataStore
    fun provideTokenDataStore(
        @ApplicationContext context: Context
    ): DataStore<Tokens> {
        return context.tokenDataStore
    }
}
