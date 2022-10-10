package com.example.zeroapp.presentation.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import antuere.domain.dto.Day
import antuere.domain.util.TimeUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException
import java.util.*

class DayAdapter(private val clickListener: DayClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DayDiffCallback) {

//    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HeaderViewHolder.TYPE_ID -> HeaderViewHolder.from(parent)
            DayViewHolder.TYPE_ID -> DayViewHolder.from(parent)
            else -> throw ClassCastException("Not valid viewType $viewType")
        }
    }

    fun addHeaderAndSubmitList(list: List<Day>?) {
//        adapterScope.launch {
        val items = when (list?.isEmpty()) {
            true, null -> listOf(DataItem.Header(TimeUtility.calendar))
            false -> listOf(DataItem.Header(TimeUtility.calendar)) + list.map {
                DataItem.DayItem(it)
            }
        }
//            withContext(Dispatchers.Main) {
//                submitList(items)
//            }
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