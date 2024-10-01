package com.example.team25.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep9Binding
import com.example.team25.domain.model.ManagerDomain
import com.example.team25.ui.reservation.adapters.ManagerRecyclerViewAdapter
import com.example.team25.ui.reservation.interfaces.OnManagerClickListener

class ReservationStep9Fragment : Fragment() {
    private var _binding: FragmentReservationStep9Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationStep9Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setManagerRecyclerView()
        navigateToPrevious()
    }

    private fun setManagerRecyclerView() {
        val managerDomainList: ArrayList<ManagerDomain> = getListFromDb()
        val managerClickListener = object : OnManagerClickListener {
            override fun onManagerClicked() {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, ReservationStep10Fragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        val adapter = ManagerRecyclerViewAdapter(managerClickListener)

        binding.managerRecyclerView.adapter = adapter
        binding.managerRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.submitList(managerDomainList)
    }

    private fun getListFromDb(): ArrayList<ManagerDomain> {
        return arrayListOf(
            ManagerDomain("김지수"),
            ManagerDomain("임지수"),
            ManagerDomain("신지수"),
            ManagerDomain("이지수"),
            ManagerDomain("박지수"),
        ) // DB에서 데이터 받아옴
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
