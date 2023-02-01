package com.example.zeroapp.presentation.base.recycle_view_animator.custom

import androidx.recyclerview.widget.RecyclerView

class DefaultCustomAnimator : CustomItemAnimator {

    override fun animateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().alpha(0f)
    }

    override fun preAnimateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.alpha = 1f
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().alpha(1f)
    }

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.alpha = 0f
    }

}