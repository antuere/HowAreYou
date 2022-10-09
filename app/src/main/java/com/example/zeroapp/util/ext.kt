package com.example.zeroapp.util

import android.graphics.Color
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
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