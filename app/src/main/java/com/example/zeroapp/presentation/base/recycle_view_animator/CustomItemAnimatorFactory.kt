package com.example.zeroapp.presentation.base.recycle_view_animator

import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.zeroapp.presentation.base.recycle_view_animator.custom.CustomItemAnimator

class CustomItemAnimatorFactory : BaseItemAnimator {

    private var defaultAnimator: CustomItemAnimator
    private val animators: MutableMap<Int, CustomItemAnimator> = mutableMapOf()

    constructor(defaultAnimator: CustomItemAnimator) {
        this.defaultAnimator = defaultAnimator
    }

    constructor(defaultAnimator: CustomItemAnimator, interpolator: Interpolator) {
        this.defaultAnimator = defaultAnimator
        this.interpolator = interpolator
    }

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        val animator = animators.getOrElse(holder.itemViewType) { defaultAnimator }
        holder.itemView.animate().apply {
            duration = removeDuration
            interpolator = this@CustomItemAnimatorFactory.interpolator
            setListener(DefaultRemoveAnimatorListener(holder))
            startDelay = getRemoveDelay(holder)

            animator.animateRemove(holder)
        }.start()

    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        val animator = animators.getOrElse(holder.itemViewType) { defaultAnimator }
        holder.itemView.animate().apply {
            duration = addDuration
            interpolator = this@CustomItemAnimatorFactory.interpolator
            setListener(DefaultAddAnimatorListener(holder))
            startDelay = getAddDelay(holder)

            animator.animateAdd(holder)
        }.start()
    }

    override fun preAnimateRemoveImpl(holder: RecyclerView.ViewHolder) {
        val animator = animators.getOrElse(holder.itemViewType) { defaultAnimator }
        animator.preAnimateRemove(holder)
    }

    override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {
        val animator = animators.getOrElse(holder.itemViewType) { defaultAnimator }
        animator.preAnimateAdd(holder)
    }

    fun addViewTypeAnimator(viewType: Int, animator: CustomItemAnimator) {
        animators[viewType] = animator
    }

}