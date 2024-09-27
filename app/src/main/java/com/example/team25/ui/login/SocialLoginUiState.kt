package com.example.team25.ui.login

sealed interface SocialLoginUiState {
    object SocialLoginUiSuccess : SocialLoginUiState

    object SocialLoginUiFail : SocialLoginUiState

    object IDle : SocialLoginUiState

    object KakaoSocialLoginUi : SocialLoginUiState
}
