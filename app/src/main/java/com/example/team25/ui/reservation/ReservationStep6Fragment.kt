package com.example.team25.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep6Binding

class ReservationStep6Fragment : Fragment() {
    private var _binding: FragmentReservationStep6Binding? = null
    private val binding get() = _binding!!
    private lateinit var selectedAddress: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationStep6Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createWeb()
        navigateToPrevious()
    }

    inner class AndroidBridge {
        @JavascriptInterface
        fun processAddress(address: String) {
            // 선택한 주소를 EditText에 표시
            selectedAddress = address

            requireActivity().runOnUiThread {
                binding.roadAddressEditText.setText(address)
            }
        }
        // 추후 다시 보완 (좌표 가져와야함)
    }

    private fun createWeb() {
        binding.roadAddressEditText.setOnClickListener {
            showWebViewDialog()
        }
    }

    private fun showWebViewDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.search_address_dialog, null)
        val builder = AlertDialog.Builder(requireContext()).setView(dialogView)
        val alertDialog = builder.create()
        val webView = dialogView.findViewById<WebView>(R.id.dialog_webview)

        webView.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }
        webView.webChromeClient = WebChromeClient()
        webView.addJavascriptInterface(AndroidBridge(), "androidInterface")

        alertDialog.show()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.previousBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
