package com.kakaotech.team25.ui.reservation

import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.FragmentReservationStep6Binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ReservationStep6Fragment : Fragment() {
    private var _binding: FragmentReservationStep6Binding? = null
    private val binding get() = _binding!!
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep6Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        navigateToPrevious()
        navigateToNext()
    }

    private inner class WebViewData {
        @JavascriptInterface
        fun getAddress(zoneCode: String, roadAddress: String, buildingName: String, sido: String) {

            CoroutineScope(Dispatchers.Default).launch {

                withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {

                    binding.roadAddressEditText.setText("($zoneCode) $roadAddress $buildingName")
                    binding.fullscreenWebView.visibility= View.GONE
                    reservationInfoViewModel.updateSido(sido)
                    reservationInfoViewModel.updateDeparture("($zoneCode) $roadAddress $buildingName")

                }
            }
        }
    }

    private fun setupWebView() {
        binding.roadAddressEditText.setOnClickListener {
            binding.fullscreenWebView.visibility = View.VISIBLE // WebView 표시
            loadAddressSearchPage()
        }
        binding.main.setOnTouchListener { _, _ ->
            if (binding.fullscreenWebView.visibility == View.VISIBLE) {
                binding.fullscreenWebView.visibility = View.GONE
            }
            false
        }

    }

    private fun loadAddressSearchPage() {
        binding.fullscreenWebView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }

        binding.fullscreenWebView.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    return false
                }

                override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                    handler?.proceed() // SSL 오류 무시
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
                    val newWebView = WebView(requireContext()).apply {
                        settings.javaScriptEnabled = true
                    }

                    newWebView.webChromeClient = object : WebChromeClient() {
                        override fun onCloseWindow(window: WebView?) {
                            newWebView.visibility = View.GONE // 창 닫을 때 숨기기
                        }
                    }

                    (resultMsg?.obj as? WebView.WebViewTransport)?.webView = newWebView
                    resultMsg?.sendToTarget()
                    return true
                }
            }

            binding.fullscreenWebView.addJavascriptInterface(WebViewData(), "androidInterface")
            loadUrl("https://ollagaljido.net/address") // URL 로드
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.previousBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            val roadAddress = binding.roadAddressEditText.text.toString()
            val detailAddress = binding.detailAddressEditText.text.toString()

            if (roadAddress.isEmpty() || detailAddress.isEmpty()) {
                Toast.makeText(requireContext(), "주소를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val departure = "$roadAddress $detailAddress"
            reservationInfoViewModel.updateDeparture(departure)

            Log.d("ReservationInfo", reservationInfoViewModel.reservationInfo.value.toString())

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationStep7Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
