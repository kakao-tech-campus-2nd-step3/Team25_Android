package com.example.team25.ui.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.domain.model.ManagerDomain
import com.example.team25.domain.repository.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManagerDataViewModel @Inject constructor(private val repository: ManagerRepository) : ViewModel() {
    private val _managers = MutableStateFlow<List<ManagerDomain>>(emptyList())
    val managers: StateFlow<List<ManagerDomain>> = _managers

    init {
        fetchManagers()
    }

    private fun fetchManagers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repository.fetchManagers() }
            updateManagers(getAllManagers())
        }
    }

    private fun updateManagers(managers: List<ManagerDomain>) {
        viewModelScope.launch {
            _managers.value = managers
        }
    }

    private suspend fun getManagersByName(name: String): List<ManagerDomain> {
        return repository.getManagersByName(name)
    }

    private suspend fun getAllManagers(): List<ManagerDomain> {
        return repository.getAllManagers()
    }

    fun updateManagersByName(name: String) {
        viewModelScope.launch {
            updateManagers(getManagersByName(name))
        }
    }
}
