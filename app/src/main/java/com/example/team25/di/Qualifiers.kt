package com.example.team25.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class KakaoRetrofit
