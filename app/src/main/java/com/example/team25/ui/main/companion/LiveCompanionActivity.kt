package com.example.team25.ui.main.companion

import LiveCompanionRecyclerViewAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.R
import com.example.team25.databinding.ActivityLiveCompanionBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles

class LiveCompanionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLiveCompanionBinding
    private var sampleLatitude = 37.55
    private var sampleLongitude = 126.98

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveCompanionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMapView()
        setLiveCompanionRecyclerViewAdapter()
        navigateToPrevious()
    }

    private fun startMapView() {
        binding.mapView.start(
            createMapLifeCycleCallback(),
            createMapReadyCallback(),
        )
    }

    private fun createMapLifeCycleCallback(): MapLifeCycleCallback {
        return object : MapLifeCycleCallback() {
            override fun onMapDestroy() {}

            override fun onMapError(error: Exception?) {}
        }
    }

    private fun createMapReadyCallback(): KakaoMapReadyCallback {
        return object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                val labelManager = kakaoMap.labelManager
                if (labelManager != null) {
                    addLabelsToMap(labelManager)
                }
            }

            override fun getPosition(): LatLng {
                return LatLng.from(sampleLatitude, sampleLongitude)
            }
        }
    }

    private fun addLabelsToMap(labelManager: LabelManager) {
        val styles =
            LabelStyles.from(
                LabelStyle.from(R.drawable.marker).setZoomLevel(8),
            )

        val labelOptions =
            LabelOptions.from(
                LatLng.from(sampleLatitude, sampleLongitude),
            )
                .setStyles(styles)

        labelManager.layer?.addLabel(labelOptions)
    }

    private fun setLiveCompanionRecyclerViewAdapter() {
        val adapter = LiveCompanionRecyclerViewAdapter()
        binding.liveCompanionRecyclerView.adapter = adapter
        binding.liveCompanionRecyclerView.layoutManager = LinearLayoutManager(this)

        testLiveCompanionRecyclerViewAdapter(adapter)
    }

    private fun testLiveCompanionRecyclerViewAdapter(adapter: LiveCompanionRecyclerViewAdapter) {
        val mock =
            listOf(
                "24.08.26 21:00 방문 픽업",
                "24.08.26 21:04 부산대 병원 도착",
                "24.08.26 21:04 병원 접수 완료",
            )

        adapter.submitList(mock)
    }

    private fun navigateToPrevious() {
        binding.mapPreviousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @Override
    override fun onResume() {
        super.onResume()
        binding.mapView.resume()
    }

    @Override
    public override fun onPause() {
        super.onPause()
        binding.mapView.pause()
    }
}
