package com.example.zeroapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.use
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.google.android.material.transition.MaterialContainerTransform


val Fragment.mainActivity: MainActivity?
    get() = activity as? MainActivity

fun Fragment.startOnClickAnimation(view: View) {
    view.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.on_click_anim))
}

fun Fragment.createSharedElementEnterTransition() = MaterialContainerTransform().apply {
    drawingViewId = R.id.myNavHostFragment
    duration = resources.getInteger(R.integer.duration_normal).toLong()
    scrimColor = Color.TRANSPARENT
    setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorOnPrimary))
}

fun View.getSmileResFromBtnId() : Int {
    return when (this.id) {
        R.id.b_very_happy -> antuere.data.R.drawable.smile_very_happy
        R.id.b_happySmile -> antuere.data.R.drawable.smile_happy
        R.id.b_smile_low -> antuere.data.R.drawable.smile_low
        R.id.b_none -> antuere.data.R.drawable.smile_none
        R.id.b_sad -> antuere.data.R.drawable.smile_sad
        else -> antuere.data.R.drawable.smile_none
    }
}

fun <T> LiveData<T>.toMutableLiveData(): MutableLiveData<T> {
    val mediatorLiveData = MediatorLiveData<T>()
    mediatorLiveData.addSource(this) {
        mediatorLiveData.value = it
    }
    return mediatorLiveData
}

@ColorInt
@SuppressLint("Recycle")
fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

fun GridLayoutManager.setManagerSpanCount(value: Int) {
    this.spanCount = value
    this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (position) {
                0 -> value
                else -> 1
            }
        }
    }
}