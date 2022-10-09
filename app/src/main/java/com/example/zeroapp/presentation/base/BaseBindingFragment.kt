package com.example.zeroapp.presentation.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.example.zeroapp.util.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.flow.StateFlow

abstract class BaseBindingFragment<T : ViewBinding>(protected val inflater: (LayoutInflater, ViewGroup?, Boolean) -> T) :
    Fragment() {

    protected var binding: T? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = this.inflater(inflater, container, false)
        return binding?.root
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}