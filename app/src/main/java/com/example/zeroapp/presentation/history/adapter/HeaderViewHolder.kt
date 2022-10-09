package com.example.zeroapp.presentation.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeroapp.databinding.HeaderHistoryBinding
import com.example.zeroapp.util.getMonthTitle
import timber.log.Timber
import java.util.Calendar

class HeaderViewHolder private constructor(private val binding: HeaderHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val TYPE_ID = 0
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