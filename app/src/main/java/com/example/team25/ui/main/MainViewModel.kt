package com.example.team25.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.di.IoDispatcher
import com.example.team25.domain.usecase.LogoutUseCase
import com.example.team25.domain.usecase.WithdrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _withdrawEvent = MutableStateFlow<WithdrawStatus>(WithdrawStatus.Idle)
    val withdrawEvent: StateFlow<WithdrawStatus> = _withdrawEvent

    fun logout() {
        viewModelScope.launch(ioDispatcher) {
            logoutUseCase()
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            val withdrawResult = withdrawUseCase()
            _withdrawEvent.value = if (withdrawResult != null) {
                WithdrawStatus.Success
            } else {
                WithdrawStatus.Failure
            }
        }
    }
}
