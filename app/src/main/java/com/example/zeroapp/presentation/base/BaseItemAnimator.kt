package com.example.zeroapp.presentation.base

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.example.zeroapp.R

class BaseItemAnimator : DefaultItemAnimator() {

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.animation =
            AnimationUtils.loadAnimation(holder?.itemView?.context, R.anim.recycle_view_remove_anim)
        return super.animateRemove(holder)
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {

        holder?.itemView?.animation =
            AnimationUtils.loadAnimation(holder?.itemView?.context, R.anim.recycle_view_add_anim)
        return super.animateAdd(holder)
    }

    override fun getAddDuration(): Long {
        return 400
    }

    override fun getRemoveDuration(): Long {
        return 500
    }
}