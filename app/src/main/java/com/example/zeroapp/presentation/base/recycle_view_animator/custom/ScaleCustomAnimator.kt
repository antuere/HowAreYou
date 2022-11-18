package com.example.zeroapp.presentation.base.recycle_view_animator.custom

import androidx.recyclerview.widget.RecyclerView

class ScaleCustomAnimator: CustomItemAnimator {

    override fun animateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().scaleX(0.0F)
        holder.itemView.animate().scaleY(0.0F)
//        holder.itemView.animate().translationX(-holder.itemView.rootView.width.toFloat())
        holder.itemView.animate().alpha(0.1F)
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder) {
//        holder.itemView.animate().translationX(0f)
        holder.itemView.animate().scaleX(1.0F)
        holder.itemView.animate().scaleY(1.0F)
        holder.itemView.animate().alpha(1.0F)
    }

    override fun preAnimateAdd(holder: RecyclerView.ViewHolder) {
        holder.itemView.scaleX = 0F
        holder.itemView.scaleY = 0F
        holder.itemView.alpha = 0.1F
//        holder.itemView.translationX = -holder.itemView.rootView.width.toFloat()
    }

    override fun preAnimateRemove(holder: RecyclerView.ViewHolder) {
        holder.itemView.scaleX = 1F
        holder.itemView.scaleY = 1F
//        holder.itemView.translationX = 0F
        holder.itemView.alpha = 1F
    }
}