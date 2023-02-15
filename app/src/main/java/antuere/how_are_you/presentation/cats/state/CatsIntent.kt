package antuere.how_are_you.presentation.cats.state

import android.graphics.Bitmap

sealed interface CatsIntent {
    object UpdateCatsClicked : CatsIntent
    data class CatOnLongClicked(val bitmap: Bitmap?) : CatsIntent
    data class WriteExternalPermissionCalled(val isGranted: Boolean) : CatsIntent
}