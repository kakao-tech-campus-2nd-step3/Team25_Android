package com.example.team25.ui.main.status.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.team25.databinding.ItemReservationHistoryBinding
import com.example.team25.ui.main.status.data.ReservationInfo
import com.example.team25.ui.main.status.interfaces.OnCheckReportClickListener
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationHistoryRecyclerViewAdapter(private val clicklistener: OnCheckReportClickListener) :
    ListAdapter<ReservationInfo, ReservationHistoryRecyclerViewAdapter.ReservationHistoryViewHolder>(
        DiffCallback(),
    ) {
    class ReservationHistoryViewHolder(
        private val binding: ItemReservationHistoryBinding,
        private val clicklistener: OnCheckReportClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTextView.text = item.name
            binding.reservationDateTextView.text = dateFormat.format(item.date)

            binding.checkReportBtn.setOnClickListener {
                clicklistener.onCheckReportClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationHistoryViewHolder {
        val binding = ItemReservationHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationHistoryViewHolder(binding, clicklistener)
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
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ReservationInfo,
            newItem: ReservationInfo,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
