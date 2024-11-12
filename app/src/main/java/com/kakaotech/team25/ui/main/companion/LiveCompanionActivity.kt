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
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.ActivityLiveCompanionBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableDeferred
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
        navigateToPrevious()
    }

    private fun collectAccompanyInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                liveCompanionViewModel.accompanyInfoList.collectLatest { accompanyInfoList ->
                    if (accompanyInfoList.isEmpty()) binding.liveCompanionRecyclerView.visibility = View.GONE
                    else (binding.liveCompanionRecyclerView.adapter as? LiveCompanionRecyclerViewAdapter)
                        ?.submitList(accompanyInfoList.map { it.statusDescribe })
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
