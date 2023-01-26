package antuere.how_are_you.presentation.base.app_state

import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState

interface AppState {

    fun showDialog(dialog: UIDialog)

    fun showSnackbar(message: String, duration: Long = 2000L)

    fun dismissSnackbar()

    fun updateAppBar(newState: AppBarState)

    fun navigateUp()
}