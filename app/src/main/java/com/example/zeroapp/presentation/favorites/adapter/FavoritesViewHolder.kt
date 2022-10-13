package com.example.zeroapp.presentation.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import antuere.domain.dto.Day
import com.example.zeroapp.R
import com.example.zeroapp.databinding.DayItemBinding
import com.example.zeroapp.presentation.history.adapter.DayClickListener

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
            imageView.setImageResource(item.imageId)
            dateText.text = item.currentDateString
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

