package com.example.team25.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.data.network.dto.AccountLoginDto
import com.example.team25.data.network.dto.TokenDto
import com.example.team25.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val accountLoginDto = AccountLoginDto(username)
            val tokenDto: TokenDto? = loginUseCase(accountLoginDto)
            if (tokenDto != null) {
                _loginState.value = LoginState.Success(tokenDto)
            } else {
                _loginState.value = LoginState.Error("로그인 실패")
            }
        }
    }

    fun updateErrorMessage(message: String) {
        _loginState.update { currentState ->
            if (currentState is LoginState.Error) {
                currentState.copy(message = message)
            } else {
                LoginState.Error(message)
            }
        }
    }
}
