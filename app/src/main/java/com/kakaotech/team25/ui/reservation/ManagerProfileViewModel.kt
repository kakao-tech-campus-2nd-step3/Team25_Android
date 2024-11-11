package com.kakaotech.team25.ui.reservation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.data.network.dto.ProfileDto
import com.kakaotech.team25.domain.Gender
import com.kakaotech.team25.domain.usecase.GetImageUriUseCase
import com.kakaotech.team25.domain.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getImageUriUseCase: GetImageUriUseCase,
):ViewModel() {
    companion object {
        private const val TAG = "ProfileViewModel"
    }

    enum class ProfileLoadStatus {
        LOADING,
        SUCCESS,
        FAILURE
    }

    val defaultTime = "00:00"

    private val _profileLoadStatus = MutableStateFlow(ProfileLoadStatus.LOADING)
    val profileLoadStatus: StateFlow<ProfileLoadStatus> = _profileLoadStatus

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _workingRegion = MutableStateFlow("")
    val workingRegion: StateFlow<String> = _workingRegion

    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> = _profileImage

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri

    private val _profileImageUrl = MutableStateFlow("")
    val profileImageUrl: StateFlow<String> = _profileImageUrl

    private val _gender = MutableStateFlow(Gender.MALE)
    val gender: StateFlow<Gender> = _gender

    private val _career = MutableStateFlow("")
    val career: StateFlow<String> = _career

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _monStartTime = MutableStateFlow("00:00")
    val monStartTime: StateFlow<String> = _monStartTime

    private val _monEndTime = MutableStateFlow("00:00")
    val monEndTime: StateFlow<String> = _monEndTime

    private val _tueStartTime = MutableStateFlow("00:00")
    val tueStartTime: StateFlow<String> = _tueStartTime

    private val _tueEndTime = MutableStateFlow("00:00")
    val tueEndTime: StateFlow<String> = _tueEndTime

    private val _wedStartTime = MutableStateFlow("00:00")
    val wedStartTime: StateFlow<String> = _wedStartTime

    private val _wedEndTime = MutableStateFlow("00:00")
    val wedEndTime: StateFlow<String> = _wedEndTime

    private val _thuStartTime = MutableStateFlow("00:00")
    val thuStartTime: StateFlow<String> = _thuStartTime

    private val _thuEndTime = MutableStateFlow("00:00")
    val thuEndTime: StateFlow<String> = _thuEndTime

    private val _friStartTime = MutableStateFlow("00:00")
    val friStartTime: StateFlow<String> = _friStartTime

    private val _friEndTime = MutableStateFlow("00:00")
    val friEndTime: StateFlow<String> = _friEndTime

    private val _satStartTime = MutableStateFlow("00:00")
    val satStartTime: StateFlow<String> = _satStartTime

    private val _satEndTime = MutableStateFlow("00:00")
    val satEndTime: StateFlow<String> = _satEndTime

    private val _sunStartTime = MutableStateFlow("00:00")
    val sunStartTime: StateFlow<String> = _sunStartTime

    private val _sunEndTime = MutableStateFlow("00:00")
    val sunEndTime: StateFlow<String> = _sunEndTime

    fun getProfile(managerId: String) {
        viewModelScope.launch {
            resetProfileData()
            val profile: ProfileDto? = getProfileUseCase(managerId)
            if (profile == null) {
                Log.e(TAG, "Profile data is null or failed to load")
                _profileLoadStatus.value = ProfileLoadStatus.FAILURE
            } else {
                _profileLoadStatus.value = ProfileLoadStatus.SUCCESS
                _name.value = profile.data?.name ?: "Unknown"
                _profileImage.value = profile.data?.profileImage ?: ""
                if (_profileImage.value != "") {
                    getProfileImage(_profileImage.value)
                }
                _gender.value = if (profile.data?.gender == "여성") Gender.FEMALE else Gender.MALE
                _career.value = profile.data?.career ?: "경력 없음"
                _comment.value = profile.data?.comment ?: "한 마디를 등록해주세요"
                _workingRegion.value = profile.data?.workingRegion ?: "지역을 등록해주세요"

                val workingHour = profile.data?.workingHour
                if (workingHour != null) {
                    _monStartTime.value = workingHour.monStartTime ?: defaultTime
                    _monEndTime.value = workingHour.monEndTime ?: defaultTime
                    _tueStartTime.value = workingHour.tueStartTime ?: defaultTime
                    _tueEndTime.value = workingHour.tueEndTime ?: defaultTime
                    _wedStartTime.value = workingHour.wedStartTime ?: defaultTime
                    _wedEndTime.value = workingHour.wedEndTime ?: defaultTime
                    _thuStartTime.value = workingHour.thuStartTime ?: defaultTime
                    _thuEndTime.value = workingHour.thuEndTime ?: defaultTime
                    _friStartTime.value = workingHour.friStartTime ?: defaultTime
                    _friEndTime.value = workingHour.friEndTime ?: defaultTime
                    _satStartTime.value = workingHour.satStartTime ?: defaultTime
                    _satEndTime.value = workingHour.satEndTime ?: defaultTime
                    _sunStartTime.value = workingHour.sunStartTime ?: defaultTime
                    _sunEndTime.value = workingHour.sunEndTime ?: defaultTime
                }

            }
        }
    }

    private fun getProfileImage(s3url: String) {
        viewModelScope.launch {
            val downloadedImageUri = getImageUriUseCase(s3url)
            if (downloadedImageUri != null) {
                _profileImageUri.value = downloadedImageUri
            }
        }
    }

    private fun resetProfileData() {
        _name.value = "Unknown"
        _workingRegion.value = ""
        _career.value = "No career info"
        _comment.value = "No comment available"
        _profileImage.value = ""
        _gender.value = Gender.MALE

        _monStartTime.value = defaultTime
        _monEndTime.value = defaultTime
        _tueStartTime.value = defaultTime
        _tueEndTime.value = defaultTime
        _wedStartTime.value = defaultTime
        _wedEndTime.value = defaultTime
        _thuStartTime.value = defaultTime
        _thuEndTime.value = defaultTime
        _friStartTime.value = defaultTime
        _friEndTime.value = defaultTime
        _satStartTime.value = defaultTime
        _satEndTime.value = defaultTime
        _sunStartTime.value = defaultTime
        _sunEndTime.value = defaultTime
    }

}
