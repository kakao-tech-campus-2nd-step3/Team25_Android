package com.example.team25.ui.main.companion

import LiveCompanionRecyclerViewAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.R
import com.example.team25.databinding.ActivityLiveCompanionBinding
import com.example.team25.domain.model.AccompanyInfo
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
    private val kakaoMapDeferred = CompletableDeferred<KakaoMap>()
    private val liveCompanionViewModel: LiveCompanionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveCompanionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMapView()
        setLiveCompanionRecyclerViewAdapter()
        collectAccompanyInfo()
        collectCoordinateInfo()
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
                kakaoMapDeferred.complete(kakaoMap)
            }
        }
    }

    private fun updateLabelsToMap(labelManager: LabelManager, latLng: LatLng) {
        val styles =
            LabelStyles.from(
                LabelStyle.from(R.drawable.marker).setZoomLevel(8),
            )

        val labelOptions =
            LabelOptions.from(
                LatLng.from(latLng),
            )
                .setStyles(styles)

        labelManager.layer?.removeAll()
        labelManager.layer?.addLabel(labelOptions)
    }

    private fun updateMapLocation(kakaoMap: KakaoMap, latLng: LatLng) {
        val labelManager = kakaoMap.labelManager

        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(latLng))

        if (labelManager != null) updateLabelsToMap(labelManager, latLng)
    }

    private fun collectAccompanyInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                liveCompanionViewModel.accompanyInfoList.collectLatest { accompanyInfoList ->
                    (binding.liveCompanionRecyclerView.adapter as? LiveCompanionRecyclerViewAdapter)
                        ?.submitList(accompanyInfoList.map { it.statusDescribe })
                }
            }
        }
    }

    private fun collectCoordinateInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                val kakaoMap = kakaoMapDeferred.await()
                liveCompanionViewModel.coordinateInfo.collectLatest { coordinates ->
                    Log.d("pjh","coord")
                    updateMapLocation(kakaoMap,coordinates)
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
