package com.example.zeroapp.presentation.base.ui_compose_components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UIDialogListener {
    private var currentDialog by mutableStateOf<UIDialog?>(null)

    fun showDialog(uiDialog: UIDialog) {
        currentDialog = uiDialog
    }

    @Composable
    fun SetupDialogListener() {
        currentDialog?.let {
            Dialog(dialog = it, closeDialog = { currentDialog = null })
        }
    }
}