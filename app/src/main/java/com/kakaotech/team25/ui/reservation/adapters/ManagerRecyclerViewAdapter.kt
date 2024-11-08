package com.kakaotech.team25.ui.reservation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakaotech.team25.databinding.ItemManagerBinding
import com.kakaotech.team25.domain.model.ManagerDomain
import com.kakaotech.team25.ui.reservation.interfaces.OnManagerClickListener

class ManagerRecyclerViewAdapter(private val clickListener: OnManagerClickListener) :
    ListAdapter<ManagerDomain, ManagerRecyclerViewAdapter.ManagerViewHolder>(ManagerDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ManagerViewHolder {
        val binding = ItemManagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManagerViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(
        holder: ManagerViewHolder,
        position: Int,
    ) {
        val manager = getItem(position)
        holder.bind(manager)
    }

    class ManagerViewHolder(
        private val binding: ItemManagerBinding,
        private val clickListener: OnManagerClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var selectedManagerDomain: ManagerDomain

        init {
            itemView.setOnClickListener {
                clickListener.onManagerClicked(selectedManagerDomain)
            }
        }

        fun bind(managerDomain: ManagerDomain) {
            selectedManagerDomain = managerDomain
            binding.profileName.text = managerDomain.name
        }
    }

    private class ManagerDiffCallback : DiffUtil.ItemCallback<ManagerDomain>() {
        override fun areItemsTheSame(
            oldItem: ManagerDomain,
            newItem: ManagerDomain,
        ): Boolean {
            return oldItem.managerId == newItem.managerId
        }

        override fun areContentsTheSame(
            oldItem: ManagerDomain,
            newItem: ManagerDomain,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
