package com.kakaotech.team25.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakaotech.team25.BuildConfig
import com.kakaotech.team25.data.network.KakaoApi
import com.kakaotech.team25.data.network.authenticator.HttpAuthenticator
import com.kakaotech.team25.data.network.calladapter.ResultCallAdapter
import com.kakaotech.team25.data.network.interceptor.TokenInterceptor
import com.kakaotech.team25.data.network.remote.PaymentApiService
import com.kakaotech.team25.data.network.services.RemoteSearchHospitalService
import com.kakaotech.team25.data.network.typeadapter.LocalDateTypeAdapter
import com.kakaotech.team25.data.remote.AccompanyApiService
import com.kakaotech.team25.data.remote.ManagerApiService
import com.kakaotech.team25.data.remote.ReportApiService
import com.kakaotech.team25.data.remote.ReservationApiService
import com.kakaotech.team25.data.remote.SignIn
import com.kakaotech.team25.data.remote.UserService
import com.kakaotech.team25.ui.reservation.interfaces.SearchHospitalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTypeAdapter())
            .create()
    }

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
    @ServerRetrofit
    fun provideServerRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        val url = BuildConfig.API_BASE_URL

        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addCallAdapterFactory(ResultCallAdapter.Factory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchHospitalService(kakaoApi: KakaoApi): SearchHospitalService = RemoteSearchHospitalService(kakaoApi)

    @Provides
    @Singleton
    fun provideManagerApiService(@ServerRetrofit retrofit: Retrofit): ManagerApiService =
        retrofit.create(ManagerApiService::class.java)

    @Provides
    @Singleton
    fun provideReservationApiService(@ServerRetrofit retrofit: Retrofit): ReservationApiService =
        retrofit.create(ReservationApiService::class.java)
    @Provides
    @Singleton
    fun providePaymentApiService(@ServerRetrofit retrofit: Retrofit) : PaymentApiService =
        retrofit.create(PaymentApiService::class.java)

    @Provides
    @Singleton
    fun provideReportApiService(@ServerRetrofit retrofit: Retrofit): ReportApiService =
        retrofit.create(ReportApiService::class.java)

    @Provides
    @Singleton
    fun provideAccompanyApiService(@ServerRetrofit retrofit: Retrofit): AccompanyApiService =
        retrofit.create(AccompanyApiService::class.java)

    @Provides
    @Singleton
    fun provideUserService(@ServerRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    fun provideSignIn(@GeneralRetrofit retrofit: Retrofit): SignIn {
        return retrofit.create(SignIn::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor, httpAuthenticator: HttpAuthenticator): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .authenticator(httpAuthenticator)
            .build()
}
