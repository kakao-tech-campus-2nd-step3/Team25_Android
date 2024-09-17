package com.example.team25.ui.main

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.team25.BuildConfig
import com.example.team25.database.HospitalDatabase
import com.example.team25.domain.HospitalDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kakao.vectormap.KakaoMapSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKakaoMapSdk()
        loadHospitalsFromJson(this)
    }

    private fun initializeKakaoMapSdk() {
        KakaoMapSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }

    private fun loadHospitalsFromJson(context: Context) {
        val database = HospitalDatabase.getInstance(context)

        CoroutineScope(Dispatchers.IO).launch {
            val inputStream = context.assets.open("hospital_data_202406.json")
            val reader = InputStreamReader(inputStream, "UTF-8")
            val hospitals: List<HospitalDomain> = Gson().fromJson(
                reader, object : TypeToken<List<HospitalDomain>>() {}.type
            )

            database.hospitalDao().insertAll(hospitals)
        }
    }
}

