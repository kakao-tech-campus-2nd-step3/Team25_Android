package com.example.team25.ui.main.status.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.team25.databinding.ItemReservationStatusBinding
import com.example.team25.ui.main.status.data.ReservationInfo
import com.example.team25.ui.main.status.interfaces.OnRequestCancelClickListener
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationStatusRecyclerViewAdapter(private val clicklistener: OnRequestCancelClickListener) :
    ListAdapter<
        ReservationInfo,
        ReservationStatusRecyclerViewAdapter.ReservationStatusViewHolder,
        >(DiffCallback()) {
    class ReservationStatusViewHolder(
        private val binding: ItemReservationStatusBinding,
        private val clickListener: OnRequestCancelClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTextView.text = item.name
            binding.reservationDateTextView.text = dateFormat.format(item.date)

            binding.requestCancelBtn.setOnClickListener {
                clickListener.onRequestCancelClicked(item)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationStatusViewHolder {
        val binding = ItemReservationStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationStatusViewHolder(binding, clicklistener)
    }

    override fun onBindViewHolder(
        holder: ReservationStatusViewHolder,
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
