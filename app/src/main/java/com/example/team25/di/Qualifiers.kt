package com.example.team25.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KakaoRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeneralRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenDataStore
