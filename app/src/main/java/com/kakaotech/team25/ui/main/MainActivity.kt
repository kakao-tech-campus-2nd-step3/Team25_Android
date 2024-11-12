package com.kakaotech.team25.ui.main

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25.data.util.DateFormatter
import com.kakaotech.team25.databinding.ActivityMainBinding
import com.kakaotech.team25.domain.model.AccompanyInfo
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.ui.login.LoginEntryActivity
import com.kakaotech.team25.ui.main.companion.LiveCompanionActivity
import com.kakaotech.team25.ui.main.status.ReservationStatusActivity
import com.kakaotech.team25.ui.reservation.ReservationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeWithdrawEvent()
        navigateToLiveCompanion()
        navigateToReservationStatus()
        navigateToReservation()
        setLogoutClickListener()
        setWithdrawClickListener()
        setObserves()
        setStatusBarTransparent()
    }

    private fun observeWithdrawEvent() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.withdrawEvent.collect { event ->
                    when (event) {
                        is WithdrawStatus.Success -> {
                            navigateToLogin()
                        }

                        is WithdrawStatus.Failure -> {
                            Toast.makeText(this@MainActivity, "회원 탈퇴에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                        }

                        WithdrawStatus.Idle -> {
                        }
                    }
                }
            }
        }
    }

    private fun setWithdrawClickListener() {
        binding.withdrawTextView.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle("회원 탈퇴")
                .setMessage("정말로 회원 탈퇴를 하시겠습니까?")
                .setPositiveButton("확인") { _, _ ->
                    mainViewModel.withdraw()
                }
                .setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }

            dialogBuilder.show()

        }
    }

    private fun setLogoutClickListener() {
        binding.logoutTextView.setOnClickListener {
            mainViewModel.logout()
            navigateToLogin()
        }
    }


    private fun navigateToLiveCompanion() {
        binding.realTimeCompanionSeeAllBtn.setOnClickListener {
            val intent = Intent(this, LiveCompanionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToReservationStatus() {
        binding.reservationSeeAllBtn.setOnClickListener {
            val intent = Intent(this, ReservationStatusActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToReservation() {
        binding.goReservationBtn.setOnClickListener {
            val intent = Intent(this, ReservationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginEntryActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setObserves() {
        collectRunningReservation()
        collectAccompanyInfo()
    }

    private fun collectRunningReservation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.runningReservation.collectLatest { reservationInfo ->
                    updateReservationSeeAllBtn(reservationInfo)
                    updateRealTimeCompanionSeeAllBtn(reservationInfo)
                }
            }
        }
    }

    private fun collectAccompanyInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.accompanyInfo.collectLatest { accompanyInfo ->
                    updateAccompanyMessage(accompanyInfo)
                }
            }
        }
    }

    private fun updateReservationSeeAllBtn(reservationInfo: ReservationInfo?) {
        if (reservationInfo == null) updateReservationSeeAllBtnGone()
        else updateReservationSeeAllBtnVisible(reservationInfo)
    }

    private fun updateReservationSeeAllBtnGone() {
        binding.reservationSeeAllBtn02.visibility = View.GONE
    }

    private fun updateReservationSeeAllBtnVisible(reservationInfo: ReservationInfo) {
        binding.reservationSeeAllBtn02.visibility = View.VISIBLE

        val runningTime = DateFormatter.formatDate(
            reservationInfo.reservationDateTime, outputFormat = SimpleDateFormat("a", Locale.KOREAN)
        )

        val runningHours = DateFormatter.formatDate(
            reservationInfo.reservationDateTime, outputFormat = SimpleDateFormat("h", Locale.KOREAN)
        )

        val runningMinutes = DateFormatter.formatDate(
            reservationInfo.reservationDateTime, outputFormat = SimpleDateFormat("mm", Locale.KOREAN)
        )

        binding.timeCycleTextView.text = runningTime
        binding.hourTextView.text = runningHours
        binding.minutesTextView.text = runningMinutes
    }

    private fun updateRealTimeCompanionSeeAllBtn(reservationInfo: ReservationInfo?) {
        if (reservationInfo == null) updateRealTimeCompanionSeeAllBtnToStop()
        else updateRealTimeCompanionSeeAllBtnToRun(reservationInfo)
    }

    private fun updateRealTimeCompanionSeeAllBtnToStop() {
        binding.onSignalLayout.visibility = View.GONE
        binding.offSignalLayout.visibility = View.VISIBLE
    }

    private fun updateRealTimeCompanionSeeAllBtnToRun(reservationInfo: ReservationInfo) {
        binding.onSignalLayout.visibility = View.VISIBLE
        binding.offSignalLayout.visibility = View.GONE

        val managerName = mainViewModel.getManagerName(reservationInfo.managerId)
        binding.runningManagerNameTextView.text = managerName.toString()

        mainViewModel.updateAccompanyInfo(reservationInfo.reservationId)
    }

    private fun updateAccompanyMessage(accompanyInfo: AccompanyInfo?) {
        if (accompanyInfo == null) updateRealTimeCompanionSeeAllBtnToStop()
        else {
            val description = accompanyInfo.statusDescribe
            val maxLength = 10
            val formattedDescription =
                if (description.length > maxLength) description.substring(0, maxLength).plus("...")
                else description

            binding.accompanyMessageTextView.text = formattedDescription
            binding.accompanyDateTextView.text = DateFormatter.formatDate(accompanyInfo.statusDate)
        }
    }

    private fun setStatusBarTransparent() {
        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

}
