package com.example.team25

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import android.content.Context
import android.util.Log
import com.example.team25.BuildConfig
import com.example.team25.dao.HospitalDao
import com.example.team25.database.HospitalDatabase
import com.example.team25.domain.HospitalDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject lateinit var hospitalInitializer: HospitalInitializer

    override fun onCreate() {
        super.onCreate()
        initializeKakaoMapSdk()
        initializeKakaoSdk()
        loadHospitalsFromJson()
    }

    private fun initializeKakaoMapSdk() {
        KakaoMapSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }

    private fun initializeKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }

    private fun loadHospitalsFromJson() {
        hospitalInitializer.initialize()
    }
}

