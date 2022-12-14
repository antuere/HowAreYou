package com.example.zeroapp.presentation.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import antuere.domain.dto.Day
import com.example.zeroapp.R
import com.example.zeroapp.databinding.DayItemBinding
import com.example.zeroapp.util.SmileProvider

class DayViewHolder private constructor(private val binding: DayItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val TYPE_ID = 1
        fun from(parent: ViewGroup): DayViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DayItemBinding.inflate(layoutInflater, parent, false)
            return DayViewHolder(binding)
        }
    }

    fun bind(item: Day, clickListener: DayClickListener) {
        with(binding) {
            val resId = SmileProvider.getSmileImageByName(item.imageName)
            imageView.setImageResource(resId)
            dateText.text = item.dateString
            val transitionName =
                itemView.context.getString(R.string.transition_name_item, item.dayId.toString())
            itemView.transitionName = transitionName

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