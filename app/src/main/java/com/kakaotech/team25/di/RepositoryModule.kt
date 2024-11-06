package com.kakaotech.team25.di

import com.kakaotech.team25.data.repository.DefaultAccompanyRepository
import com.kakaotech.team25.data.repository.DefaultMainRepository
import com.kakaotech.team25.data.repository.DefaultManagerRepository
import com.kakaotech.team25.data.repository.DefaultReservationRepository
import com.kakaotech.team25.domain.repository.AccompanyRepository
import com.kakaotech.team25.domain.repository.MainRepository
import com.kakaotech.team25.domain.repository.ManagerRepository
import com.kakaotech.team25.domain.repository.ReservationRepository
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
