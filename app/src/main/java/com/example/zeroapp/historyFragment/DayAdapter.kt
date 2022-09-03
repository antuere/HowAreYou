package com.example.zeroapp.historyFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.databinding.DayItemBinding
import com.example.zeroapp.getSmileImage
import timber.log.Timber

class DayAdapter(val clickListener: DayListener) :
    ListAdapter<Day, DayAdapter.DayViewHolder>(DayDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        Timber.i("my log createViewHolder")
        return DayViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        Timber.i("my log bindViewHolder")
        holder.bind(getItem(position), clickListener)
    }


    class DayDiffCallback : DiffUtil.ItemCallback<Day>() {

        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem.dayId == newItem.dayId
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem == newItem
        }
    }

    class DayViewHolder private constructor(private val binding: DayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): DayViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DayItemBinding.inflate(layoutInflater, parent, false)
                return DayViewHolder(binding)
            }
        }

        fun bind(item: Day, clickListener: DayListener) {
            Timber.i("my log try bind item")
            with(binding) {
                imageView.setImageResource(getSmileImage(item.imageId))
                dateText.text = item.currentDate
                itemView.setOnClickListener {
                    clickListener.onClick(item)
                }
            }

        }
    }


}

class DayListener(val clickListener: (dayId: Long) -> Unit) {
    fun onClick(day: Day) {
        clickListener(day.dayId)
    }
}
