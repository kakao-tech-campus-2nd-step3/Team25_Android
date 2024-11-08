package com.kakaotech.team25.ui.main.status

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kakaotech.team25.databinding.ActivityReservationStatusBinding
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.ui.main.status.adapters.ReservationHistoryRecyclerViewAdapter
import com.kakaotech.team25.ui.main.status.adapters.ReservationStatusRecyclerViewAdapter
import com.kakaotech.team25.ui.main.status.interfaces.OnCheckReportClickListener
import com.kakaotech.team25.ui.main.status.interfaces.OnRequestCancelClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReservationStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStatusBinding
    private val reservationStatusViewModel: ReservationStatusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setReservationStatusRecyclerViewAdapter()
        setReservationHistoryRecyclerViewAdapter()
        setObserves()
    }

    private fun setReservationStatusRecyclerViewAdapter() {
        val requestCancelClickListener =
            object : OnRequestCancelClickListener {
                override fun onRequestCancelClicked(item: ReservationInfo) {
                    val intent =
                        Intent(this@ReservationStatusActivity, ReservationCancelActivity::class.java)
                            .putExtra("ReservationInfo", item)
                    startActivity(intent)
                }
            }
        val adapter = ReservationStatusRecyclerViewAdapter(requestCancelClickListener)

        binding.reservationStatusRecyclerView.adapter = adapter
        binding.reservationStatusRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setReservationHistoryRecyclerViewAdapter() {
        val checkReportClickListener =
            object : OnCheckReportClickListener {
                override fun onCheckReportClicked(item: ReservationInfo) {
                    val intent =
                        Intent(this@ReservationStatusActivity, ReservationCheckReportActivity::class.java)
                            .putExtra("ReservationInfo", item)
                    startActivity(intent)
                }
            }

        val adapter = ReservationHistoryRecyclerViewAdapter(checkReportClickListener)

        binding.reservationHistoryRecyclerView.adapter = adapter
        binding.reservationHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setObserves(){
        collectReservationStatus()
        collectReservationHistory()
    }

    private fun collectReservationStatus(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                reservationStatusViewModel.reservationStatus.collectLatest {
                    (binding.reservationStatusRecyclerView.adapter as? ReservationStatusRecyclerViewAdapter)?.submitList(it)
                }
            }
        }
    }

    private fun collectReservationHistory(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                reservationStatusViewModel.reservationHistory.collectLatest {
                    (binding.reservationHistoryRecyclerView.adapter as? ReservationHistoryRecyclerViewAdapter)?.submitList(it)
                }
            }
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
