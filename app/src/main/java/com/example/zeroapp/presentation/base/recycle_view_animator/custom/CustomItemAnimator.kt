package com.example.zeroapp.presentation.base.recycle_view_animator.custom

import androidx.recyclerview.widget.RecyclerView

interface CustomItemAnimator {
    fun animateRemove(holder: RecyclerView.ViewHolder)
    fun animateAdd(holder: RecyclerView.ViewHolder)

    fun preAnimateRemove(holder: RecyclerView.ViewHolder) {}
    fun preAnimateAdd(holder: RecyclerView.ViewHolder) {}
}

