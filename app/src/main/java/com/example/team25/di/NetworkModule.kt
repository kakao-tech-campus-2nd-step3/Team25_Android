package com.example.team25.di

import com.example.team25.BuildConfig
import com.example.team25.data.network.KakaoApi
import com.example.team25.data.network.services.RemoteSearchHospitalService
import com.example.team25.data.remote.ManagerApiService
import com.example.team25.data.remote.SignIn
import com.example.team25.ui.reservation.interfaces.SearchHospitalService
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
    @KakaoRetrofit
    fun provideKakaoRetrofit(): Retrofit {
        val url = BuildConfig.KAKAO_BASE_URL

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKakaoApi(@KakaoRetrofit retrofit: Retrofit): KakaoApi {
        return retrofit.create(KakaoApi::class.java)
    }

    @Provides
    @Singleton
    @GeneralRetrofit
    fun provideRetrofit(): Retrofit {
        val url = BuildConfig.API_BASE_URL
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchHospitalService(kakaoApi: KakaoApi): SearchHospitalService = RemoteSearchHospitalService(kakaoApi)

    @Provides
    @Singleton
    fun provideManagerApiService(@GeneralRetrofit retrofit: Retrofit): ManagerApiService =
        retrofit.create(ManagerApiService::class.java)

    @Provides
    fun provideSignIn(@GeneralRetrofit retrofit: Retrofit): SignIn {
        return retrofit.create(SignIn::class.java)
    }
}
