package com.example.zeroapp.presentation.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import antuere.domain.dto.Day
import antuere.domain.util.TimeUtility
import com.example.zeroapp.presentation.history.MyAnalystForHistory
import java.lang.ClassCastException

class DayAdapter(
    private val clickListener: DayClickListener,
    private val myAnalyst: MyAnalystForHistory
) :
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
            true, null -> emptyList()
            false -> {
                val firstDate = TimeUtility.parseLongToCalendar(list.first().dayId)
                val lastDate = TimeUtility.parseLongToCalendar(list.last().dayId)

                val title = myAnalyst.getHeaderForHistory(firstDate, lastDate)

                listOf(DataItem.Header(title)) + list.map {
                    DataItem.DayItem(it)
                }
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
                holder.bind(item.dateString)
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