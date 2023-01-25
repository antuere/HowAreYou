package antuere.how_are_you.presentation.home.state

import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface HomeSideEffect {

    data class Dialog(val uiDialog: UIDialog) : HomeSideEffect

    data class NavigationToDayDetail(val dayId: Long) :
        HomeSideEffect

    data class Snackbar(val message: UiText) : HomeSideEffect

    object NavigationToAddDay : HomeSideEffect
}