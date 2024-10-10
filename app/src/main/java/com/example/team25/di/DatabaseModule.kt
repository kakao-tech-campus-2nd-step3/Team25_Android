package com.example.team25.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.room.Room
import com.example.team25.TokensProto.Tokens
import com.example.team25.data.dao.HospitalDao
import com.example.team25.data.dao.ManagerDao
import com.example.team25.data.dao.ReservationDao
import com.example.team25.data.database.HospitalDatabase
import com.example.team25.data.database.ManagerDatabase
import com.example.team25.data.database.ReservationDatabase
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
    fun provideManagerDatabase(
        @ApplicationContext context: Context,
    ): ManagerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ManagerDatabase::class.java,
            "manager_database",
        ).build()
    }

    @Provides
    fun provideReservationDatabase(
        @ApplicationContext context: Context,
    ): ReservationDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ReservationDatabase::class.java,
            "reservation_database",
        ).build()
    }

    @Provides
    fun provideHospitalDao(database: HospitalDatabase): HospitalDao {
        return database.hospitalDao()
    }

    @Provides
    fun provideManagerDao(database: ManagerDatabase): ManagerDao {
        return database.managerDao()
    }

    @Provides
    fun provideReservationDao(database: ReservationDatabase): ReservationDao {
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
