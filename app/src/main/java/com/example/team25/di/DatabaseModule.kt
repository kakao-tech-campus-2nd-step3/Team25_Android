package com.example.team25.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.room.Room
import com.example.team25.TokensProto.Tokens
import com.example.team25.data.dao.HospitalDao
import com.example.team25.data.database.HospitalDatabase
import com.example.team25.data.database.TokenSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
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
    fun provideHospitalDao(database: HospitalDatabase): HospitalDao {
        return database.hospitalDao()
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
