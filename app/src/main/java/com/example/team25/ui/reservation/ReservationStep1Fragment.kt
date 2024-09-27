package com.example.team25.ui.reservation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep1Binding
import com.example.team25.domain.Gender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationStep1Fragment : Fragment() {
    private var _binding: FragmentReservationStep1Binding? = null
    private val binding get() = _binding!!

    private var firstPhoneNum = "010"
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setPhoneNumDropDown()
        navigateToNext()
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            val patientName = getPatientName()

            if (patientName.isEmpty()) {
                Toast.makeText(requireContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val secondPhoneNumMiddle = binding.numMiddleEditText.text.toString()
            val secondPhoneNumEnd = binding.numEndEditText.text.toString()
            if (!isValidPhoneNumber(secondPhoneNumMiddle, secondPhoneNumEnd)) {
                Toast.makeText(requireContext(), "전화번호가 올바른 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fullPhoneNumber = getFullPhoneNumber(secondPhoneNumMiddle, secondPhoneNumEnd)
            val selectedGender = getSelectedGender()

            reservationInfoViewModel.updatePatientName(patientName)
            reservationInfoViewModel.updatePatientPhone(fullPhoneNumber)
            reservationInfoViewModel.updatePatientGender(selectedGender)

            Log.d("ReservationInfo", reservationInfoViewModel.reservationInfo.value.toString())

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationStep2Fragment())
                .addToBackStack(null)
                .commit()
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

    private fun getPatientName(): String {
        return binding.nameEditText.text.toString()
    }

    private fun getFullPhoneNumber(secondPhoneNumMiddle: String, secondPhoneNumEnd: String): String {
        val secondPhoneNum = "$secondPhoneNumMiddle-$secondPhoneNumEnd"
        return "$firstPhoneNum-$secondPhoneNum"
    }

    private fun getSelectedGender(): Gender {
        return when (binding.genderRadioGroup.checkedRadioButtonId) {
            R.id.gender_man_radio_btn -> Gender.MALE
            R.id.gender_woman_radio_btn -> Gender.FEMALE
            else -> Gender.MALE
        }
    }

    private fun isValidPhoneNumber(middle: String, end: String): Boolean {
        // 중간 번호가 3자리 또는 4자리이고, 뒷 번호가 4자리인지 검사
        val regex = Regex("^(\\d{3}|\\d{4})\\d{4}$")
        return regex.matches(middle + end)
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
