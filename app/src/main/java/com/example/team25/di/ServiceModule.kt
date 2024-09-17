package com.example.team25.di

import com.example.team25.dao.HospitalDao
import com.example.team25.ui.reservation.interfaces.SearchHospitalService
import com.example.team25.ui.reservation.network.KakaoApi
import com.example.team25.ui.reservation.network.LocalSearchHospitalService
import com.example.team25.ui.reservation.network.RemoteSearchHospitalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ServiceModule {
    @Provides
    fun provideSearchHospitalService(
        hospitalDao: HospitalDao,
        kakaoApi: KakaoApi,
    ): SearchHospitalService {
        val isRemoteMode = false

        return if (isRemoteMode) {
            RemoteSearchHospitalService(kakaoApi)
        } else {
            LocalSearchHospitalService(hospitalDao)
        }
    }
}
