package com.kakaotech.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kakaotech.team25.databinding.FragmentReservationCheckBinding

class ReservationCheckFragment : Fragment() {
    private var _binding: FragmentReservationCheckBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setVehicle()
        navigateToPay()
    }

    private fun navigateToPay() {
        binding.nextBtn.setOnClickListener {
            val intent = Intent(requireContext(), ReservationPaymentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setVehicle() {
        // val vehicle = arguments?.getString("vehicle") ?: "이동 수단이 선택되지 않음"
        binding.selectedVehicleTextView.text = "vehicle"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
