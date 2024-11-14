package com.kakaotech.team25.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.kakaotech.team25.TokensProto.Tokens
import com.kakaotech.team25.data.database.TokenSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
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
