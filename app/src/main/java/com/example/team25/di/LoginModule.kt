package com.example.team25.di

import com.example.team25.data.repository.DefaultLoginRepository
import com.example.team25.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: DefaultLoginRepository): LoginRepository
}
