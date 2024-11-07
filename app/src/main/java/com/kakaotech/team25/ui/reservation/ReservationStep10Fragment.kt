package com.kakaotech.team25.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.FragmentReservationStep10Binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReservationStep10Fragment : Fragment() {
    private var _binding: FragmentReservationStep10Binding? = null

    private val managerProfileViewModel: ManagerProfileViewModel by activityViewModels()
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep10Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        observeProfileLoadStatus()
        observeInformation()
        managerProfileViewModel.getProfile(reservationInfoViewModel.getManagerId())
        navigateToPrevious()
        navigateToNext()
    }

    private fun observeInformation() {
        observeProfileImage()
        observeName()
        observeRegion()
        observeComment()
        observeCareer()
        observeTime()
    }

    private fun observeTime() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    managerProfileViewModel.monStartTime.collect { time ->
                        binding.mondayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.monEndTime.collect { time ->
                        binding.mondayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.tueStartTime.collect { time ->
                        binding.tuesdayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.tueEndTime.collect { time ->
                        binding.tuesdayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.wedStartTime.collect { time ->
                        binding.wednesdayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.wedEndTime.collect { time ->
                        binding.wednesdayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.thuStartTime.collect { time ->
                        binding.thursdayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.thuEndTime.collect { time ->
                        binding.thursdayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.friStartTime.collect { time ->
                        binding.fridayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.friEndTime.collect { time ->
                        binding.fridayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.satStartTime.collect { time ->
                        binding.saturdayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.satEndTime.collect { time ->
                        binding.saturdayEndTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.sunStartTime.collect { time ->
                        binding.sundayStartTimeTextView.text = time
                    }
                }
                launch {
                    managerProfileViewModel.sunEndTime.collect { time ->
                        binding.sundayEndTimeTextView.text = time
                    }
                }
            }
        }
    }

    private fun observeCareer() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerProfileViewModel.career.collect { career ->
                    binding.careerText.text = career
                }
            }
        }
    }

    private fun observeComment() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerProfileViewModel.comment.collect { comment ->
                    binding.introductionTextView.text = comment
                }
            }
        }
    }

    private fun observeRegion() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerProfileViewModel.workingRegion.collect { region ->
                    binding.locationTextView.text = region
                }
            }
        }
    }

    private fun observeName() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerProfileViewModel.name.collect { name ->
                    binding.nameTextView.text = name
                }
            }
        }
    }

    private fun observeProfileImage() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerProfileViewModel.profileImageUri.collect { uri ->
                    uri?.let {
                        binding.profileImgeView.setImageURI(it)
                    }
                }
            }
        }
    }

    private fun observeProfileLoadStatus() {
        lifecycleScope.launch {
            managerProfileViewModel.profileLoadStatus.collect { status ->
                when (status) {
                    ManagerProfileViewModel.ProfileLoadStatus.LOADING -> {
                        binding.loadingTextView.text = "로딩 중입니다..."
                    }
                    ManagerProfileViewModel.ProfileLoadStatus.SUCCESS -> {
                        binding.loadingTextView.visibility = View.GONE
                        binding.managerInforLayout.visibility = View.VISIBLE
                    }
                    ManagerProfileViewModel.ProfileLoadStatus.FAILURE -> {
                        binding.loadingTextView.text = "프로필 조회에 실패했습니다."
                    }
                }
            }
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationStep11Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
