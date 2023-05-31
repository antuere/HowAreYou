package antuere.how_are_you.presentation.screens.detail.state

import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog

sealed interface DetailSideEffect {
    object NavigateUp : DetailSideEffect
    object AnimateFavoriteBtn : DetailSideEffect
    object ClearFocus : DetailSideEffect
    data class Dialog(val uiDialog: UIDialog) : DetailSideEffect
}