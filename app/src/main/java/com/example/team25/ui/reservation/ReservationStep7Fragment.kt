package com.example.team25.ui.reservation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep7Binding
import com.example.team25.domain.HospitalDomain
import com.example.team25.ui.reservation.adapters.HospitalRecyclerViewAdapter
import com.example.team25.ui.reservation.interfaces.OnHospitalClickListener
import com.example.team25.ui.reservation.interfaces.SearchHospitalService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReservationStep7Fragment : Fragment() {
    @Inject
    lateinit var searchHospitalService: SearchHospitalService

    private var _binding: FragmentReservationStep7Binding? = null
    private val binding get() = _binding!!
    private lateinit var hospitalRecyclerViewAdapter: HospitalRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationStep7Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHospitalSearchListener()
        setSearchResultRecyclerView()
        navigateToPrevious()
    }

    private fun setHospitalSearchListener() {
        binding.destinationEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString()
                if (keyword.isNotBlank()) {
                    searchHospitals(keyword)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun searchHospitals(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val hospitals = searchHospitalService.getSearchedResult(keyword, 1)
                val sortedHospitals = sortHospitals(hospitals, keyword)

                // UI 업데이트는 Main Thread에서 수행해야 하므로 withContext 사용
                launch(Dispatchers.Main) {
                    hospitalRecyclerViewAdapter.submitList(sortedHospitals)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sortHospitals(hospitals: List<HospitalDomain>, keyword: String): List<HospitalDomain> {
        return hospitals
            .filter { it.name.contains(keyword) }
            .sortedWith(
                compareBy(
                    { it.name.indexOf(keyword) },
                    { it.name.length },
                )
            )
    }

    private fun setSearchResultRecyclerView() {
        val hospitalClickListener = object : OnHospitalClickListener {
            override fun onHospitalClicked(hospital: HospitalDomain) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, ReservationStep8Fragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        hospitalRecyclerViewAdapter = HospitalRecyclerViewAdapter(hospitalClickListener)
        binding.hospitalRecyclerView.adapter = hospitalRecyclerViewAdapter
        binding.hospitalRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
