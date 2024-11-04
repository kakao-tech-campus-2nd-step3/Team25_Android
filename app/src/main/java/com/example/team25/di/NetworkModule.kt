package com.example.team25.di

import com.example.team25.BuildConfig
import com.example.team25.data.network.KakaoApi
import com.example.team25.data.network.authenticator.HttpAuthenticator
import com.example.team25.data.network.calladapter.ResultCallAdapter
import com.example.team25.data.network.interceptor.TokenInterceptor
import com.example.team25.data.network.remote.PaymentApiService
import com.example.team25.data.network.services.RemoteSearchHospitalService
import com.example.team25.data.remote.ManagerApiService
import com.example.team25.data.remote.ReportApiService
import com.example.team25.data.remote.ReservationApiService
import com.example.team25.data.remote.SignIn
import com.example.team25.ui.reservation.interfaces.SearchHospitalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
    @ServerRetrofit
    fun provideServerRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val url = BuildConfig.API_BASE_URL

        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addCallAdapterFactory(ResultCallAdapter.Factory())
            .addConverterFactory(GsonConverterFactory.create())
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
