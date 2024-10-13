package com.example.team25.ui.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.domain.model.ManagerDomain
import com.example.team25.domain.repository.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
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
        /*CoroutineScope(Dispatchers.IO).launch {
            fetchManagers()
            updateManagers(repository.getAllManagers())
        }*/
        CoroutineScope(Dispatchers.IO).launch {
            repository.clearManagers()
            repository.insertManagers(
                listOf(
                    ManagerDomain(
                        managerId = "1",
                        name = "산지니",
                        profileImage = "/images/profile/sanjini.jpg",
                        career = "2012~2020 부산대학병원",
                        comment = "성심성의껏 모시겠습니다."
                    ),
                    ManagerDomain(
                        managerId = "2",
                        name = "장의사",
                        profileImage = "/images/profile/jangdoctor.jpg",
                        career = "2018~2022 성모병원",
                        comment = "성심성의껏 모시겠습니다."
                    )
                )
            )
            val managers = repository.getAllManagers()
            updateManagers(managers)
        }
    }

    private fun fetchManagers() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.fetchManagers()
        }
    }

    fun updateManagers(managers: List<ManagerDomain>) {
        _managers.value = managers
    }

    fun updateManagersByName(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            updateManagers(repository.getManagersByName(name))
        }
    }
}
