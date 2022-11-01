package com.example.zeroapp.presentation.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import antuere.domain.dto.Day
import com.example.zeroapp.R
import com.example.zeroapp.databinding.DayItemBinding
import com.example.zeroapp.presentation.history.adapter.DayClickListener
import com.example.zeroapp.util.SmileProvider

class FavoritesViewHolder private constructor(private val binding: DayItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): FavoritesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DayItemBinding.inflate(layoutInflater, parent, false)
            return FavoritesViewHolder(binding)
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

