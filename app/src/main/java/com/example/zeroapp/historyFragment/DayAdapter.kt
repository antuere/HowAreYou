package com.example.zeroapp.historyFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.databinding.DayItemBinding
import com.example.zeroapp.databinding.FragmentHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class DayAdapter : ListAdapter<Day, DayAdapter.DayViewHolder>(DayDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(getItem(position))
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

        val res = itemView.context.resources

        companion object {
            fun from(parent: ViewGroup): DayViewHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
                val binding = DayItemBinding.bind(view)
                return DayViewHolder(binding)
            }
        }

        fun bind(item: Day) {
            binding.imageView.setImageResource(
                when (item.imageId) {
                    R.id.b_very_happy -> R.drawable.smile_very_happy
                    R.id.b_happySmile -> R.drawable.smile_happy
                    R.id.b_none -> R.drawable.smile_none
                    R.id.b_smile_low -> R.drawable.smile_low
                    R.id.b_sad -> R.drawable.smile_sad
                    else -> R.drawable.smile_none
                }
            )
            binding.dateText.text = item.dayText
        }
    }

}
