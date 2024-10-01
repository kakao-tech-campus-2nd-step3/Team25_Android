package com.example.team25.di

import com.example.team25.ui.reservation.interfaces.SearchHospitalService
import com.example.team25.data.network.KakaoApi
import com.example.team25.data.network.services.RemoteSearchHospitalService
import com.example.team25.data.remote.SignIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @KakaoRetrofit
    fun provideKakaoRetrofit(): Retrofit {
        val url = "https://dapi.kakao.com/"

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideKakaoApi(retrofit: Retrofit): KakaoApi {
        return retrofit.create(KakaoApi::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        val url = "https://api.base.url/"
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideSearchHospitalService(
        kakaoApi: KakaoApi,
    ): SearchHospitalService =
        RemoteSearchHospitalService(kakaoApi)

    @Provides
    fun provideSignIn(retrofit: Retrofit): SignIn {
        return retrofit.create(SignIn::class.java)
    }
}
