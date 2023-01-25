package antuere.how_are_you.presentation.favorites.state

import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog

sealed interface FavoritesSideEffect {

    data class NavigationToDayDetail(val dayId: Long) :
        FavoritesSideEffect

    data class Dialog(val uiDialog: UIDialog) : FavoritesSideEffect
}