package com.example.zeroapp.presentation.base.ui_dialog

import kotlinx.coroutines.flow.StateFlow

interface IUIDialogAction {
    val uiDialog: StateFlow<UIDialog?>
}
