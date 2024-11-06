package com.example.team25.di

import com.example.team25.data.repository.DefaultAccompanyRepository
import com.example.team25.data.repository.DefaultMainRepository
import com.example.team25.data.repository.DefaultManagerRepository
import com.example.team25.data.repository.DefaultReservationRepository
import com.example.team25.domain.repository.AccompanyRepository
import com.example.team25.domain.repository.MainRepository
import com.example.team25.domain.repository.ManagerRepository
import com.example.team25.domain.repository.ReservationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindManagerRepository(
        defaultManagerRepository: DefaultManagerRepository
    ): ManagerRepository

    @Binds
    @ViewModelScoped
    abstract fun bindReservationRepository(
        defaultReservationRepository: DefaultReservationRepository
    ): ReservationRepository

    @Binds
    @ViewModelScoped
    abstract fun bindAccompanyRepository(
        defaultAccompanyRepository: DefaultAccompanyRepository
    ): AccompanyRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMainRepository(
        defaultMainRepository: DefaultMainRepository
    ): MainRepository
}
