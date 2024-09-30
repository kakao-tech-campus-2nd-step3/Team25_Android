package com.example.team25.ui.reservation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.team25.databinding.ItemHospitalBinding
import com.example.team25.domain.HospitalDomain
import com.example.team25.ui.reservation.interfaces.OnHospitalClickListener

class HospitalRecyclerViewAdapter(private val clickListener: OnHospitalClickListener) :
    ListAdapter<HospitalDomain, HospitalRecyclerViewAdapter.HospitalViewHolder>(HospitalDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HospitalViewHolder {
        val binding =
            ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HospitalViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(
        holder: HospitalViewHolder,
        position: Int,
    ) {
        val hospital = getItem(position)
        holder.bind(hospital)
    }

    class HospitalViewHolder(
        private val binding: ItemHospitalBinding,
        private val clickListener: OnHospitalClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var selectedHospital: HospitalDomain

        init {
            itemView.setOnClickListener {
                clickListener.onHospitalClicked(selectedHospital)
            }
        }

        fun bind(hospitalDomain: HospitalDomain) {
            selectedHospital = hospitalDomain
            binding.hospitalNameTextView.text = selectedHospital.name
            binding.hospitalAddressTextView.text = selectedHospital.address
        }
    }

    private class HospitalDiffCallback : DiffUtil.ItemCallback<HospitalDomain>() {
        override fun areItemsTheSame(
            oldItem: HospitalDomain,
            newItem: HospitalDomain,
        ): Boolean {
            return oldItem.placeId == newItem.placeId
        }

        override fun areContentsTheSame(
            oldItem: HospitalDomain,
            newItem: HospitalDomain,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
