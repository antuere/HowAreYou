package com.example.zeroapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.core.content.res.use
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.delay


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

fun View.getSmileResFromBtnId(): Int {
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

fun Context.findFragmentActivity(): FragmentActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context as FragmentActivity
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
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

@Composable
fun SnackbarHostState.ShowSnackBar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    LaunchedEffect(this) {
        this@ShowSnackBar.showSnackbar(
            message = message,
            duration = duration
        )
    }
}


@Composable
fun SnackbarHostState.ShowSnackBarExperimental(
    @StringRes messageId: Int,
    delayValue: Long = 2000,
    hideSnackbarAfterDelay: () -> Unit
) {
    val message = stringResource(id = messageId)

    LaunchedEffect(this) {
        this@ShowSnackBarExperimental.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short
        )
        delay(delayValue)
        hideSnackbarAfterDelay()
    }
}

@Composable
fun ChangeAppUiState(
    onComposing: (AppBarState, Boolean) -> Unit,
    @StringRes titleTopBar: Int,
    isShowBottomBar: Boolean
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = titleTopBar
            ),
            isShowBottomBar
        )
    }
}