package com.example.zeroapp.presentation.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import antuere.domain.dto.Day
import java.lang.ClassCastException
import java.util.*

class DayAdapter(private val clickListener: DayClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DayDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HeaderViewHolder.TYPE_ID -> HeaderViewHolder.from(parent)
            DayViewHolder.TYPE_ID -> DayViewHolder.from(parent)
            else -> throw ClassCastException("Not valid viewType $viewType")
        }
    }

    fun addHeaderAndSubmitList(list: List<Day>?) {
        val items = when (list?.isEmpty()) {
            true, null -> listOf(DataItem.Header(Calendar.getInstance()))
            false -> listOf(DataItem.Header(Calendar.getInstance())) + list.map {
                DataItem.DayItem(it)
            }
        }
        submitList(items)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DayViewHolder -> {
                val item = getItem(position) as DataItem.DayItem
                holder.bind(item.day, clickListener)
            }

            is HeaderViewHolder -> {
                val item = getItem(position) as DataItem.Header
                holder.bind(item.calendar)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> HeaderViewHolder.TYPE_ID
            is DataItem.DayItem -> DayViewHolder.TYPE_ID
        }
    }

}