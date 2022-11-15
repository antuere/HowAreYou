package com.example.zeroapp.presentation.base.ui_dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UIDialogListener(private val context: Context, private val action: IUIDialogAction) {

    private var dialog: AlertDialog? = null

    private fun buildDialog(uiDialog: UIDialog): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle(uiDialog.title)
            .setMessage(uiDialog.desc)
            .setIcon(uiDialog.icon)
            .setOnCancelListener {
                uiDialog.negativeButton.onClick.invoke()
            }
            .setNegativeButton(uiDialog.negativeButton.text) { _, _ ->
                uiDialog.negativeButton.onClick.invoke()
            }
            .setPositiveButton(uiDialog.positiveButton.text) { _, _ ->
                uiDialog.positiveButton.onClick.invoke()
            }
            .create()
    }

    private fun buildDialogWithNeutralBtn(uiDialog: UIDialog): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle(uiDialog.title)
            .setMessage(uiDialog.desc)
            .setIcon(uiDialog.icon)
            .setCancelable(false)
            .setNeutralButton(uiDialog.neutralButton!!.text) { _, _ ->
                uiDialog.neutralButton.onClick.invoke()
            }
            .setNegativeButton(uiDialog.negativeButton.text) { _, _ ->
                uiDialog.negativeButton.onClick.invoke()
            }
            .setPositiveButton(uiDialog.positiveButton.text) { _, _ ->
                uiDialog.positiveButton.onClick.invoke()
            }
            .create()
    }

    fun collect(lifecycleOwner: LifecycleOwner, withNeutralBtn: Boolean = false) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            action.uiDialog.collectLatest { uiDialog ->
                if (uiDialog == null) {
                    dialog?.dismiss()
                    dialog = null
                } else if (dialog == null) {
                    dialog = if (withNeutralBtn) {
                        buildDialogWithNeutralBtn(uiDialog).also { it.show() }
                    } else {
                        buildDialog(uiDialog).also { it.show() }
                    }
                }

            }
        }
    }
}