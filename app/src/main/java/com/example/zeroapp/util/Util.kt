package com.example.zeroapp.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    val context = LocalContext.current
    Toast.makeText(context, text, duration).show()
}

fun showToastByContext(
    context: Context,
    text: String? = null,
    @StringRes resId: Int? = null,
    duration: Int = Toast.LENGTH_SHORT
) {
    var toastText = text

    resId?.let {
        toastText = context.resources.getString(it)

    }

    Toast.makeText(context, toastText, duration).show()
}