package com.example.zeroapp.presentation.base.recycle_view_animator.custom

import androidx.recyclerview.widget.RecyclerView

class SlideInTopCustomAnimator: CustomItemAnimator {

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.translationY = -holder.itemView.height.toFloat() * 2
        holder.itemView.alpha = 0f
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().translationY(0f)
        holder.itemView.animate().alpha(1f)
    }


    override fun animateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().translationY(-holder.itemView.height.toFloat())
    }

}