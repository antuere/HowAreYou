package com.example.zeroapp.util

import android.content.Context
import androidx.annotation.StringRes

class ResourcesProvider(private val context: Context) {

    fun getString(@StringRes ResId: Int): String {
        return context.getString(ResId)
    }

}