package com.example.zeroapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.use
import com.example.zeroapp.detailFragment.DetailViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

fun showAlertDialog(model: MyExtendedViewModel, dayId: Long, context: Context?) {
    val lisneter = DialogInterface.OnClickListener { _, id ->
        when (id) {
            DialogInterface.BUTTON_POSITIVE -> {
                model.deleteDay(dayId)
                if (model is DetailViewModel) model.navigateToHistory()
            }
            DialogInterface.BUTTON_NEGATIVE -> Unit
        }
    }

    val dialog = AlertDialog.Builder(context!!)
        .setTitle(R.string.dialog_delete_title)
        .setMessage(R.string.dialog_delete_message)
        .setPositiveButton(R.string.yes, lisneter)
        .setNegativeButton(R.string.no, lisneter)
        .create()

    dialog.show()
}

fun showMaterialDialog(
    model: MyExtendedViewModel,
    dayId: Long,
    context: Context?,
) {
    val materialDialog = MaterialAlertDialogBuilder(
        context!!)
        .setTitle(R.string.dialog_delete_title)
        .setMessage(R.string.dialog_delete_message)
        .setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
        .setPositiveButton(R.string.yes) { dialog, _ ->
            model.deleteDay(dayId)
            if (model is DetailViewModel) model.navigateToHistory()
            dialog.dismiss()

        }
    materialDialog.show()
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