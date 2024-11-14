package com.kakaotech.team25.ui.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.domain.model.ManagerDomain
import com.kakaotech.team25.domain.repository.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerDataViewModel @Inject constructor(
    private val managerRepository: ManagerRepository
) : ViewModel() {
    private val _managers = MutableStateFlow<List<ManagerDomain>>(emptyList())
    val managers: StateFlow<List<ManagerDomain>> = _managers

    fun updateManagers(date: String, region: String) {
        viewModelScope.launch {
            _managers.value = managerRepository.getManagersFlow(date, region)
                .firstOrNull() ?: emptyList()
        }
    }

    fun updateManagersByName(name: String) {
        viewModelScope.launch {
            _managers.value = _managers.value.filter { it.name == name }
        }
    }
}
