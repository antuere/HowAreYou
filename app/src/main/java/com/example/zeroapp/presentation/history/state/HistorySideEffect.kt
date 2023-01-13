package com.example.zeroapp.presentation.history.state

import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog

sealed interface HistorySideEffect {
    data class NavigationToDayDetail(val dayId : Long) :
        HistorySideEffect

    data class Dialog(val uiDialog: UIDialog) : HistorySideEffect

    object AnimationToggleGroup : HistorySideEffect
}