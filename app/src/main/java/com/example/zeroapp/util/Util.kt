package com.example.zeroapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.use
import com.example.zeroapp.R


// Конвертер id кнопки к id изображения нужного смайлика
fun getSmileImage(id: Int): Int {
    return when (id) {
        R.id.b_very_happy -> R.drawable.smile_very_happy
        R.id.b_happySmile -> R.drawable.smile_happy
        R.id.b_none -> R.drawable.smile_none
        R.id.b_smile_low -> R.drawable.smile_low
        R.id.b_sad -> R.drawable.smile_sad
        else -> R.drawable.smile_none
    }
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

fun getMonthTitle(context: Context, num : Int) : String{
    return when (num){
        0 -> context.resources.getString(R.string.january)
        1 -> context.resources.getString(R.string.february)
        2 -> context.resources.getString(R.string.march)
        3 -> context.resources.getString(R.string.april)
        4 -> context.resources.getString(R.string.may)
        5 -> context.resources.getString(R.string.june)
        6 -> context.resources.getString(R.string.july)
        7 -> context.resources.getString(R.string.august)
        8 -> context.resources.getString(R.string.september)
        9 -> context.resources.getString(R.string.october)
        10 -> context.resources.getString(R.string.november)
        11 -> context.resources.getString(R.string.december)
        else -> throw IllegalArgumentException("Invalid number of month")
    }
}

