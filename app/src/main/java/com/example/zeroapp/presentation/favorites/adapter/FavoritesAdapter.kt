package com.example.zeroapp.presentation.favorites.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import antuere.domain.dto.Day
import com.example.zeroapp.presentation.history.adapter.DayClickListener

class FavoritesAdapter(private val clickListener: DayClickListener) :
    ListAdapter<Day, FavoritesViewHolder>(FavoritesDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

}