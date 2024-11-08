package com.kakaotech.team25.di

import com.kakaotech.team25.domain.repository.ManagerRepository
import com.kakaotech.team25.domain.repository.ReservationRepository
import com.kakaotech.team25.domain.usecase.LoadReservationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideLoadReservationsUseCase(
        reservationRepository: ReservationRepository,
        managerRepository: ManagerRepository
    ): LoadReservationsUseCase {
        return LoadReservationsUseCase(reservationRepository, managerRepository)
    }
}
