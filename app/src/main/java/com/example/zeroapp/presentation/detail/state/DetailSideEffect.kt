package com.example.zeroapp.presentation.detail.state

import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog

sealed interface DetailSideEffect {

    object NavigateUp : DetailSideEffect

    object AnimateFavoriteBtn : DetailSideEffect

    data class Dialog(val uiDialog: UIDialog) : DetailSideEffect

}