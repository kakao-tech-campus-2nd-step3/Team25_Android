package com.kakaotech.team25.ui.main.companion

import LiveCompanionRecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kakaotech.team25.databinding.ActivityLiveCompanionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LiveCompanionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLiveCompanionBinding
    private val liveCompanionViewModel: LiveCompanionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveCompanionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLiveCompanionRecyclerViewAdapter()
        collectAccompanyInfo()
        collectReservationId()
        navigateToPrevious()
    }

    private fun collectAccompanyInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                liveCompanionViewModel.accompanyInfo.collectLatest { accompanyInfoList ->
                    if (accompanyInfoList.isNullOrEmpty()) {
                        binding.liveCompanionRecyclerView.visibility = View.GONE
                        binding.notCompanionTextView.visibility = View.VISIBLE
                    }
                    else {
                        binding.liveCompanionRecyclerView.visibility = View.VISIBLE
                        binding.notCompanionTextView.visibility = View.GONE
                        (binding.liveCompanionRecyclerView.adapter as? LiveCompanionRecyclerViewAdapter)
                            ?.submitList(accompanyInfoList.map { it.statusDescribe })
                    }
                }
            }
        }
    }

    private fun collectReservationId() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                liveCompanionViewModel.reservationId.collectLatest { reservationId ->
                    reservationId?.let {
                        liveCompanionViewModel.updateAccompanyInfo(it)
                    }
                }
            }
        }
    }

    private fun setLiveCompanionRecyclerViewAdapter() {
        val adapter = LiveCompanionRecyclerViewAdapter()
        binding.liveCompanionRecyclerView.adapter = adapter
        binding.liveCompanionRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun navigateToPrevious() {
        binding.mapPreviousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
