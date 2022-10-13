package com.example.zeroapp.presentation.favorites.adapter

import androidx.recyclerview.widget.DiffUtil
import antuere.domain.dto.Day

object FavoritesDiffCallback : DiffUtil.ItemCallback<Day>() {

    override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
        return oldItem.dayId == newItem.dayId
    }

    override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
        return oldItem == newItem
    }
}