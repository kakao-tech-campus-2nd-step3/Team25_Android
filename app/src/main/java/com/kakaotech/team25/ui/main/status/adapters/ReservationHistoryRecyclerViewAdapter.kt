package com.kakaotech.team25.ui.main.status.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakaotech.team25.data.util.DateFormatter
import com.kakaotech.team25.databinding.ItemReservationHistoryBinding
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.ui.main.status.interfaces.OnCheckReportClickListener

class ReservationHistoryRecyclerViewAdapter(private val clickListener: OnCheckReportClickListener) :
    ListAdapter<ReservationInfo, ReservationHistoryRecyclerViewAdapter.ReservationHistoryViewHolder>(
        DiffCallback(),
    ) {
    class ReservationHistoryViewHolder(
        private val binding: ItemReservationHistoryBinding,
        private val clickListener: OnCheckReportClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            binding.userNameTextView.text = item.managerName
            binding.reservationDateTextView.text = DateFormatter.formatDate(item.reservationDateTime)

            binding.checkReportBtn.setOnClickListener {
                clickListener.onCheckReportClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationHistoryViewHolder {
        val binding = ItemReservationHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationHistoryViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(
        holder: ReservationHistoryViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class DiffCallback : DiffUtil.ItemCallback<ReservationInfo>() {
        override fun areItemsTheSame(
            oldItem: ReservationInfo,
            newItem: ReservationInfo,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ReservationInfo,
            newItem: ReservationInfo,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
