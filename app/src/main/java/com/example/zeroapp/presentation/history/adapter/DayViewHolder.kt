package com.example.zeroapp.presentation.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import antuere.domain.dto.Day
import com.example.zeroapp.R
import com.example.zeroapp.databinding.DayItemBinding

class DayViewHolder private constructor(private val binding: DayItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val TYPE_ID = R.layout.day_item
        fun from(parent: ViewGroup): DayViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DayItemBinding.inflate(layoutInflater, parent, false)
            return DayViewHolder(binding)
        }
    }

    fun bind(item: Day, clickListener: DayClickListener) {
        with(binding) {
            imageView.setImageResource(item.imageResId)
            dateText.text = item.dateString

            itemView.transitionName = item.transitionName

            itemView.setOnClickListener {
                clickListener.onClick(item, itemView)
            }
            itemView.setOnLongClickListener {
                clickListener.onClickLong(item)
                true
            }

        }
    }
}