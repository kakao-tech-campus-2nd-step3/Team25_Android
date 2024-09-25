package com.example.team25.ui.main.status.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.team25.databinding.ItemReservationHistoryBinding
import com.example.team25.ui.main.status.ReservationCheckReportActivity
import com.example.team25.ui.main.status.data.ReservationInfo
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationHistoryRecyclerViewAdapter : ListAdapter<ReservationInfo, ReservationHistoryRecyclerViewAdapter.ReservationHistoryViewHolder>(
    DiffCallback(),
) {
    class ReservationHistoryViewHolder(val binding: ItemReservationHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTextView.text = item.name
            binding.reservationDateTextView.text = dateFormat.format(item.date)

            binding.checkReportBtn.setOnClickListener {
                val intent =
                    Intent(binding.root.context, ReservationCheckReportActivity::class.java)
                        .putExtra("ReservationInfo", item)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationHistoryViewHolder {
        val binding = ItemReservationHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationHistoryViewHolder(binding)
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
