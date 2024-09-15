package com.example.team25.ui.main

import android.app.Application
import com.example.team25.BuildConfig
import com.kakao.vectormap.KakaoMapSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKakaoMapSdk()
    }

    private fun initializeKakaoMapSdk() {
        KakaoMapSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }
}

