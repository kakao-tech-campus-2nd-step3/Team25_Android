package com.example.team25.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _socialLoginUiState = MutableStateFlow<SocialLoginUiState>(SocialLoginUiState.IDle)
    val socialLoginUiState = _socialLoginUiState.asStateFlow()

    fun kakaoLogin() {
        _socialLoginUiState.value = SocialLoginUiState.KakaoSocialLoginUi
    }

    fun kakaoLoginSuccess() {
        _socialLoginUiState.tryEmit(SocialLoginUiState.SocialLoginUiSuccess)
    }

    fun kakaoLoginFail() {
        _socialLoginUiState.tryEmit(SocialLoginUiState.SocialLoginUiFail)
    }

    fun setUiStateIdle() {
        _socialLoginUiState.tryEmit(SocialLoginUiState.IDle)
    }
}
