package antuere.how_are_you.presentation.screens.cats.state

import android.graphics.Bitmap
import antuere.domain.dto.ImageSource

sealed interface CatsIntent {
    object UpdateCatsClicked : CatsIntent
    object ChooseSourceClicked : CatsIntent
    object ChooseDialogClose : CatsIntent
    data class CatOnLongClicked(val bitmap: Bitmap?) : CatsIntent
    data class WriteExternalPermissionCalled(val isGranted: Boolean) : CatsIntent
    data class ImageSourceSelected(val imageSource: ImageSource) : CatsIntent
    data class ImageSourceNameClicked(val imageSourceUrl: String) : CatsIntent
}