package com.kakaotech.team25.ui.main.status.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakaotech.team25.databinding.ItemReservationHistoryBinding
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.ui.main.status.interfaces.OnCheckReportClickListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationHistoryRecyclerViewAdapter(private val clicklistener: OnCheckReportClickListener) :
    ListAdapter<ReservationInfo, ReservationHistoryRecyclerViewAdapter.ReservationHistoryViewHolder>(
        DiffCallback(),
    ) {
    class ReservationHistoryViewHolder(
        private val binding: ItemReservationHistoryBinding,
        private val clicklistener: OnCheckReportClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREAN)
            val outputFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTextView.text = item.managerName
            val dateString = item.reservationDateTime
            val date = try {
                dateString?.let { inputFormat.parse(it) }
            } catch (e: ParseException) {
                null
            }

            val formattedDate = date?.let { outputFormat.format(it) } ?: "날짜 없음"
            binding.reservationDateTextView.text = formattedDate

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
