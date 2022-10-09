package com.example.zeroapp.presentation.base.ui_dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.zeroapp.R
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
            .setNegativeButton(uiDialog.negativeButton.text) { dialog, _ ->
                uiDialog.negativeButton.onClick.invoke()
            }
            .setPositiveButton(uiDialog.positiveButton.text) { dialog, _ ->
                uiDialog.positiveButton.onClick.invoke()
            }
            .create()

    }

    fun collect(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            action.uiDialog.collectLatest { uiDialog ->
                if (uiDialog == null) {
                    dialog?.dismiss()
                    dialog = null
                } else if (dialog == null)
                    dialog = buildDialog(uiDialog).also { it.show() }
            }
        }
    }
}