package antuere.how_are_you.presentation.base.ui_compose_components.dialog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class UIDialog(
    @StringRes val title: Int,
    @StringRes val desc: Int,
    @DrawableRes val icon: Int,
    val positiveButton: UiButton,
    val negativeButton: UiButton? = null,
    val dismissAction: () -> Unit = {}
) {

    data class UiButton(
        @StringRes val text: Int,
        val onClick: () -> Unit = {}
    )

}
