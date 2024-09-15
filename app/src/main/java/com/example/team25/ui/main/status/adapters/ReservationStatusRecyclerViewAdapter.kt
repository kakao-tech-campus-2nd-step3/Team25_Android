package com.example.team25.ui.main.status.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.team25.databinding.ItemReservationStatusBinding
import com.example.team25.ui.main.status.ReservationCancelActivity
import com.example.team25.ui.main.status.data.ReservationInfo
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationStatusRecyclerViewAdapter :
    ListAdapter<
        ReservationInfo,
        ReservationStatusRecyclerViewAdapter.ReservationStatusViewHolder,
        >(DiffCallback()) {
    inner class ReservationStatusViewHolder(val binding: ItemReservationStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTextView.text = item.name
            binding.reservationDateTextView.text = dateFormat.format(item.date)

            binding.requestCancelBtn.setOnClickListener {
                val intent =
                    Intent(binding.root.context, ReservationCancelActivity::class.java)
                        .putExtra("ReservationInfo", item)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationStatusViewHolder {
        val binding = ItemReservationStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationStatusViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReservationStatusViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<ReservationInfo>() {
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
            return oldItem.name == newItem.name && oldItem.date == newItem.date
        }
    }
}
