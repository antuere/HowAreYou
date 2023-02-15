package antuere.how_are_you.presentation.cats

import android.graphics.Bitmap
import android.os.Build
import antuere.how_are_you.R
import antuere.how_are_you.presentation.cats.state.CatsIntent
import antuere.how_are_you.presentation.cats.state.CatsState
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.cats.state.CatsSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class CatsViewModel @Inject constructor() :
    ViewModelMvi<CatsState, CatsSideEffect, CatsIntent>() {

    override val container: Container<CatsState, CatsSideEffect> = container(CatsState())

    private var catsImageBitmap: Bitmap? = null

    override fun onIntent(intent: CatsIntent) {
        when (intent) {
            CatsIntent.UpdateCatsClicked -> updateState {
                state.copy(urlList = state.urlList.reversed())
            }
            is CatsIntent.CatOnLongClicked -> {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    catsImageBitmap = intent.bitmap
                    sideEffect(CatsSideEffect.RequestPermission)
                } else {
                    saveImageToGallery(intent.bitmap)
                }
            }
            is CatsIntent.WriteExternalPermissionCalled -> {
                if (intent.isGranted) {
                    saveImageToGallery(catsImageBitmap)
                } else {
                    sideEffect(CatsSideEffect.Snackbar(UiText.StringResource(R.string.permission_denied_cats)))
                }
            }
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap?) {
        if (bitmap != null) {
            val onSuccessSaved = {
                sideEffect(CatsSideEffect.Vibration)
                sideEffect(CatsSideEffect.Snackbar(UiText.StringResource(R.string.saved_complete_cats)))
            }
            sideEffect(
                CatsSideEffect.SaveImageToGallery(
                    bitmap = bitmap,
                    onSuccessSaved = onSuccessSaved
                )
            )
        } else {
            sideEffect(CatsSideEffect.Snackbar(UiText.StringResource(R.string.saved_error_cats)))
        }
    }
}