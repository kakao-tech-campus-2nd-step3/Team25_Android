package com.kakaotech.team25.ui.reservation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.FragmentReservationStep4Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationStep4Fragment : Fragment() {
    private var _binding: FragmentReservationStep4Binding? = null
    private val binding get() = _binding!!
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()
    private var firstPhoneNum = "010"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setPhoneNumDropDown()
        restoreForm()
        navigateToPrevious()
        navigateToNext()
    }

    private fun restoreForm() {
        val savedPhoneNumber = reservationInfoViewModel.reservationInfo.value.patient.nokPhone

        if (savedPhoneNumber.isNotEmpty()) {
            val phoneParts = savedPhoneNumber.split("-")
            firstPhoneNum = phoneParts[0]
            val secondPhoneNumMiddle = phoneParts[1]
            val secondPhoneNumEnd = phoneParts[2]

            binding.phoneNumAutoCompleteTextView.setText(firstPhoneNum, false)
            binding.numMiddleEditText.setText(secondPhoneNumMiddle)
            binding.numEndEditText.setText(secondPhoneNumEnd)
        }
    }

    private fun setPhoneNumDropDown() {
        val phoneNumOptions = resources.getStringArray(R.array.phone_num_option)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, phoneNumOptions)
        binding.phoneNumAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.phoneNumAutoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            firstPhoneNum = parent.getItemAtPosition(position).toString()
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
            val secondPhoneNumMiddle = binding.numMiddleEditText.text.toString()
            val secondPhoneNumEnd = binding.numEndEditText.text.toString()
            if (!isValidPhoneNumber(secondPhoneNumMiddle, secondPhoneNumEnd)) {
                Toast.makeText(requireContext(), "전화번호가 올바른 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fullPhoneNumber = getFullPhoneNumber(secondPhoneNumMiddle, secondPhoneNumEnd)

            reservationInfoViewModel.updateNokPhone(fullPhoneNumber)
            Log.d("ReservationInfo", reservationInfoViewModel.reservationInfo.value.toString())

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationStep5Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun isValidPhoneNumber(
        middle: String,
        end: String,
    ): Boolean {
        // 중간 번호가 3자리 또는 4자리이고, 뒷 번호가 4자리인지 검사
        val regex = Regex("^(\\d{3}|\\d{4})\\d{4}$")
        return regex.matches(middle + end)
    }

    private fun getFullPhoneNumber(
        secondPhoneNumMiddle: String,
        secondPhoneNumEnd: String,
    ): String {
        val secondPhoneNum = "$secondPhoneNumMiddle-$secondPhoneNumEnd"
        return "$firstPhoneNum-$secondPhoneNum"
    }

    override fun onResume() {
        super.onResume()
        setPhoneNumDropDown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
