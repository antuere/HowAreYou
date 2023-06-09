package antuere.how_are_you.presentation.screens.cats

import android.graphics.Bitmap
import android.os.Build
import androidx.lifecycle.viewModelScope
import antuere.domain.repository.ImageSourceRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.cats.state.CatsIntent
import antuere.how_are_you.presentation.screens.cats.state.CatsSideEffect
import antuere.how_are_you.presentation.screens.cats.state.CatsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class CatsViewModel @Inject constructor(
    private val imageSourceRepository: ImageSourceRepository,
) :
    ViewModelMvi<CatsState, CatsSideEffect, CatsIntent>() {

    override val container: Container<CatsState, CatsSideEffect> = container(CatsState())

    private var catsImageBitmap: Bitmap? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val savedSource = imageSourceRepository.getCurrentImageSource().first()
            updateState { state.copy(imageSource = savedSource, isLoading = false) }
        }
    }

    override fun onIntent(intent: CatsIntent) {
        when (intent) {
            CatsIntent.UpdateCatsClicked -> updateState {
                state.copy(forceRecompositionFlag = !state.forceRecompositionFlag)
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

            CatsIntent.ChooseSourceClicked -> updateState {
                state.copy(isShowSourceSelectionDialog = true)
            }

            is CatsIntent.ImageSourceSelected -> {
                if (state.imageSource != intent.imageSource) {
                    updateState {
                        state.copy(imageSource = intent.imageSource)
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        imageSourceRepository.saveImageSource(intent.imageSource)
                    }
                }
                updateState {
                    state.copy(isShowSourceSelectionDialog = false)
                }
            }

            CatsIntent.ChooseDialogClose -> updateState {
                state.copy(isShowSourceSelectionDialog = false)
            }

            is CatsIntent.ImageSourceNameClicked -> sideEffect(
                CatsSideEffect.NavigateToSourceWebsite(
                    intent.imageSourceUrl
                )
            )
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