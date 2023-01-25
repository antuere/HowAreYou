package antuere.how_are_you.presentation.history.state

import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog

sealed interface HistorySideEffect {
    data class NavigationToDayDetail(val dayId : Long) :
        HistorySideEffect

    data class Dialog(val uiDialog: UIDialog) : HistorySideEffect

    object AnimationHistoryHeader : HistorySideEffect
}