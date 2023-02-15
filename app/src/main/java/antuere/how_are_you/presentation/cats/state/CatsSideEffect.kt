package antuere.how_are_you.presentation.cats.state

import android.graphics.Bitmap
import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface CatsSideEffect {
    data class SaveImageToGallery(val bitmap: Bitmap, val onSuccessSaved: () -> Unit) :
        CatsSideEffect

    data class Snackbar(val message: UiText) : CatsSideEffect
    object RequestPermission : CatsSideEffect
    object Vibration : CatsSideEffect
}