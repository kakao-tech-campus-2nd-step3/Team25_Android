package com.kakaotech.team25.ui.main.status.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakaotech.team25.databinding.ItemReservationStatusBinding
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.ui.main.status.interfaces.OnRequestCancelClickListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationStatusRecyclerViewAdapter(private val clicklistener: OnRequestCancelClickListener) :
    ListAdapter<
        ReservationInfo,
        ReservationStatusRecyclerViewAdapter.ReservationStatusViewHolder,
        >(DiffCallback()) {
    class ReservationStatusViewHolder(
        private val binding: ItemReservationStatusBinding,
        private val clickListener: OnRequestCancelClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREAN)
            val outputFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)
            val dateString = item.reservationDateTime
            val date = try {
                dateString?.let { inputFormat.parse(it) }
            } catch (e: ParseException) {
                null
            }

            val formattedDate = date?.let { outputFormat.format(it) } ?: "날짜 없음"

            binding.userNameTextView.text = item.managerName
            binding.reservationDateTextView.text = formattedDate

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
