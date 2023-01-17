package com.example.zeroapp.presentation.favorites.state

import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.history.state.HistorySideEffect

sealed interface FavoritesSideEffect {

    data class NavigationToDayDetail(val dayId: Long) :
        FavoritesSideEffect

    data class Dialog(val uiDialog: UIDialog) : FavoritesSideEffect
}