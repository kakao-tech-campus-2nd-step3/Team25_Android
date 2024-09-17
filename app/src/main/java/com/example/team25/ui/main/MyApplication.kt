package com.example.team25.ui.main

import android.app.Application
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
    @Inject
    lateinit var hospitalDao: HospitalDao
    override fun onCreate() {
        super.onCreate()
        initializeKakaoMapSdk()
        loadHospitalsFromJson()
    }

    private fun initializeKakaoMapSdk() {
        KakaoMapSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }

    private fun loadHospitalsFromJson() {
        CoroutineScope(Dispatchers.IO).launch {
            val isDatabaseEmpty = hospitalDao.getAnyHospital() == null

            if (isDatabaseEmpty) {
                val inputStream = assets.open("hospital_data_202406.json")
                val reader = InputStreamReader(inputStream, "UTF-8")
                val hospitals: List<HospitalDomain> = Gson().fromJson(
                    reader, object : TypeToken<List<HospitalDomain>>() {}.type
                )
                hospitalDao.insertAll(hospitals)
            }
        }
    }
}

