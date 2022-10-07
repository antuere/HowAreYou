package com.example.zeroapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.use
import com.example.zeroapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder



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

// Builder dialog for delete day (Material 2)
fun showAlertDialog(model: MyExtendedViewModel, dayId: Long, context: Context?) {
    val listener = DialogInterface.OnClickListener { _, id ->
        when (id) {
            DialogInterface.BUTTON_POSITIVE -> {
                model.deleteDay(dayId)
            }
            DialogInterface.BUTTON_NEGATIVE -> Unit
        }
    }

    val dialog = AlertDialog.Builder(context!!)
        .setTitle(R.string.dialog_delete_title)
        .setMessage(R.string.dialog_delete_message)
        .setPositiveButton(R.string.yes, listener)
        .setNegativeButton(R.string.no, listener)
        .create()

    dialog.show()
}

// Builder dialog for delete day (Material 3)
fun buildMaterialDialog(
    onClick: (dayId: Long) -> Unit,
    dayId: Long,
    context: Context?,
): MaterialAlertDialogBuilder {
    val materialDialog = MaterialAlertDialogBuilder(
        context!!
    )
        .setTitle(R.string.dialog_delete_title)
        .setMessage(R.string.dialog_delete_message)
        .setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
        .setPositiveButton(R.string.yes) { dialog, _ ->
            onClick(dayId)
            dialog.dismiss()

        }
    return materialDialog
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

