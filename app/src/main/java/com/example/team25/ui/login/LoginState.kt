package com.example.team25.ui.login

import com.example.team25.data.network.dto.TokenDto

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val token: TokenDto) : LoginState()
    data class Error(val message: String) : LoginState()
}
