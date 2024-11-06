package com.kakaotech.team25.ui.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.domain.model.ManagerDomain
import com.kakaotech.team25.domain.repository.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerDataViewModel
@Inject constructor(private val repository: ManagerRepository) : ViewModel() {
    private val managerName = MutableStateFlow<String>("")
    val managers: StateFlow<List<ManagerDomain>> = combine(
        repository.managersFlow,
        managerName
    ) { managers, managerName ->
        if (managerName.isEmpty()) {
            managers
        } else {
            managers.filter { it.name.contains(managerName) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    init {
        fetchManagers()
    }

    private fun fetchManagers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchManagers()
        }
    }

    fun updateManagersByName(name: String) {
        viewModelScope.launch {
            managerName.value = name
        }
    }
}
