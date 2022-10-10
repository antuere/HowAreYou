package com.example.zeroapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.use
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.google.android.material.transition.MaterialContainerTransform


val Fragment.mainActivity: MainActivity?
    get() = activity as? MainActivity

fun Fragment.setToolbarIcon(@DrawableRes resId: Int) {
    mainActivity?.toolbar?.setNavigationIcon(resId)
}

fun Fragment.createSharedElementEnterTransition() = MaterialContainerTransform().apply {
    drawingViewId = R.id.myNavHostFragment
    duration = resources.getInteger(R.integer.duration_normal).toLong()
    scrimColor = Color.WHITE
    setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorOnPrimary))
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