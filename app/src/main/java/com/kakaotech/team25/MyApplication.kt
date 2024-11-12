package com.kakaotech.team25

import android.app.Application
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.kakao.sdk.common.KakaoSdk
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKakaoSdk()
        initializeTransferNetworkLossHandler()
    }

    private fun initializeKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }

    private fun initializeTransferNetworkLossHandler() {
        TransferNetworkLossHandler.getInstance(this)
    }
}
