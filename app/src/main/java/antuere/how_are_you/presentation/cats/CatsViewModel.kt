package antuere.how_are_you.presentation.cats

import antuere.how_are_you.R
import antuere.how_are_you.presentation.cats.state.CatsIntent
import antuere.how_are_you.presentation.cats.state.CatsState
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.cats.state.CatsSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class CatsViewModel @Inject constructor() :
    ViewModelMvi<CatsState, CatsSideEffect, CatsIntent>() {

    override val container: Container<CatsState, CatsSideEffect> = container(CatsState())

    override fun onIntent(intent: CatsIntent) {
        when (intent) {
            CatsIntent.UpdateCatsClicked -> updateState {
                state.copy(urlList = state.urlList.reversed())
            }
            is CatsIntent.CatOnLongClicked -> {
                if (intent.bitmap != null) {
                    val onSuccessSaved = {
                        sideEffect(CatsSideEffect.Snackbar(UiText.StringResource(R.string.saved_complete_cats)))
                    }

                    sideEffect(
                        CatsSideEffect.SaveImageToGallery(
                            bitmap = intent.bitmap,
                            onSuccessSaved = onSuccessSaved
                        )
                    )
                } else {
                    sideEffect(CatsSideEffect.Snackbar(UiText.StringResource(R.string.saved_error_cats)))
                }
            }
        }
    }
}