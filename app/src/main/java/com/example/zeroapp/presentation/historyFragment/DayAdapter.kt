package com.example.zeroapp.presentation.historyFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeroapp.R
import antuere.domain.Day
import com.example.zeroapp.databinding.DayItemBinding
import com.example.zeroapp.databinding.HeaderHistoryBinding
import com.example.zeroapp.util.getMonthTitle
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.ClassCastException
import java.util.*


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_DAY = 1

class DayAdapter(private val clickListener: DayClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DayDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_DAY -> DayViewHolder.from(parent)
            else -> throw ClassCastException("Not valid viewType $viewType")
        }
    }

    fun addHeaderAndSubmitList(list: List<Day>?) {
        adapterScope.launch {
            val items = when (list?.isEmpty()) {
                true, null -> listOf(DataItem.Header(Calendar.getInstance()))
                false -> listOf(DataItem.Header(Calendar.getInstance())) + list.map {
                    DataItem.DayItem(it)
                }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }

        }
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
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.DayItem -> ITEM_VIEW_TYPE_DAY
        }
    }

    class DayDiffCallback : DiffUtil.ItemCallback<DataItem>() {

        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
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

        fun bind(item: Day, clickListener: DayClickListener) {
            Timber.i("my log bind item day")
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
                Timber.i("my log itemView transName = ${itemView.transitionName}")
                Timber.i("my log root transName = ${root.transitionName}")

            }
        }
    }


    class HeaderViewHolder private constructor(private val binding: HeaderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HeaderHistoryBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }

        fun bind(calendar: Calendar) {
            Timber.i("my log bind item header")
            val month = calendar.get(Calendar.MONTH)
            binding.header.text = getMonthTitle(itemView.context!!, month)
        }

    }


}

interface DayClickListener {

    fun onClick(day: Day, view: View)

    fun onClickLong(day: Day)
}

sealed class DataItem {

    data class DayItem(val day: Day) : DataItem() {
        override val id = day.dayId

    }

    data class Header(val calendar: Calendar) : DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}

//class DayListener(val clickListener: (dayId: Long) -> Unit) {
//    fun onClick(day: Day) {
//        clickListener(day.dayId)
//    }
//
//    fun onLongClick(day: Day) {
//        clickListener(day.dayId)
//    }
//}