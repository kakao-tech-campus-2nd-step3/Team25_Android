package com.example.team25.di

import com.example.team25.ui.reservation.interfaces.SearchHospitalService
import com.example.team25.ui.reservation.network.KakaoApi
import com.example.team25.ui.reservation.services.RemoteSearchHospitalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val url = "https://dapi.kakao.com/"

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKakaoApi(retrofit: Retrofit): KakaoApi {
        return retrofit.create(KakaoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchHospitalService(
        kakaoApi: KakaoApi,
    ): SearchHospitalService =
        RemoteSearchHospitalService(kakaoApi)
}
