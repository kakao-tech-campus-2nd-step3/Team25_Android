package com.example.team25.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _socialLoginUiState = MutableStateFlow<SocialLoginUiState>(SocialLoginUiState.IDle)
    val socialLoginUiState = _socialLoginUiState.asStateFlow()

    fun kakaoLogin() {
        _socialLoginUiState.value = SocialLoginUiState.KakaoSocialLoginUi
    }

    fun kakaoLoginSuccess() {
        _socialLoginUiState.update { SocialLoginUiState.SocialLoginUiSuccess }
    }

    fun kakaoLoginFail() {
        _socialLoginUiState.update { SocialLoginUiState.SocialLoginUiFail }
    }

    fun setUiStateIdle() {
        _socialLoginUiState.update { SocialLoginUiState.IDle }
    }
}
