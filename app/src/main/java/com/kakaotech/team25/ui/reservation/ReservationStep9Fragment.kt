package com.kakaotech.team25.ui.reservation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.FragmentReservationStep9Binding
import com.kakaotech.team25.domain.model.ManagerDomain
import com.kakaotech.team25.ui.reservation.adapters.ManagerRecyclerViewAdapter
import com.kakaotech.team25.ui.reservation.interfaces.OnManagerClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReservationStep9Fragment : Fragment() {
    private var _binding: FragmentReservationStep9Binding? = null
    private val binding get() = _binding!!
    private val managerViewModel: ManagerDataViewModel by activityViewModels()
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep9Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setManagerRecyclerView()
        collectReservationInfo()
        collectManagerData()
        setManagerSearchListener()
        navigateToPrevious()
    }

    private fun collectReservationInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                reservationInfoViewModel.reservationInfo.collectLatest {reservationInfo ->
                    val date = reservationInfo.reservationDateTime.substringBefore(" ")
                    val region = reservationInfo.sido

                    Log.d("datt", date)

                    managerViewModel.fetchManagers(date, "부산광역시 남구")
                }
            }
        }
    }

    private fun collectManagerData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                managerViewModel.managers.collectLatest {
                    (binding.managerRecyclerView.adapter as? ManagerRecyclerViewAdapter)?.submitList(it)
                }
            }
        }
    }

    private fun setManagerRecyclerView() {
        val managerClickListener = object : OnManagerClickListener {
            override fun onManagerClicked(item: ManagerDomain) {
                reservationInfoViewModel.updateManagerId(item.managerId)
                reservationInfoViewModel.updateManagerName(item.name)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, ReservationStep10Fragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        val adapter = ManagerRecyclerViewAdapter(managerClickListener)

        binding.managerRecyclerView.adapter = adapter
        binding.managerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setManagerSearchListener() {
        binding.searchManagerEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                managerViewModel.updateManagersByName(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
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
