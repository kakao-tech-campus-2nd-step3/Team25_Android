package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.R
import com.example.team25.databinding.ActivityReservationStep6Binding

class ReservationStep6Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep6Binding
    private lateinit var selectedAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        createWeb()

        navigateToPrevious()
        navigateToNext()
    }

    inner class AndroidBridge {
        @JavascriptInterface
        fun processAddress(address: String) {
            // 선택한 주소를 EditText에 표시
            selectedAddress = address

            runOnUiThread {
                binding.roadAddressEditText.setText(address)
            }
        }
        // 추후 다시 보완 (좌표 가져와야함)
    }

    private fun createWeb()  {
        binding.roadAddressEditText.setOnClickListener {
            showWebViewDialog()
        }
    }

    private fun showWebViewDialog()  {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.search_address_dialog, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = builder.create()
        val webView = dialogView.findViewById<WebView>(R.id.dialog_webview)

        webView.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }
        webView.webChromeClient = WebChromeClient()
        // webView.loadUrl("file:///android_asset/www/search_address.html") // HTTP 서버를 통해 HTML 파일을 제공하도록 설정하는 것이 좋습니다.
        webView.addJavascriptInterface(AndroidBridge(), "androidInterface")

        alertDialog.show()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.previousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, ReservationStep7Activity::class.java)
            startActivity(intent)
        }
    }
}
