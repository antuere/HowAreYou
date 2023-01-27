package antuere.how_are_you.presentation.favorites

import androidx.lifecycle.*
import antuere.domain.repository.DayRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.favorites.state.FavoritesIntent
import antuere.how_are_you.presentation.favorites.state.FavoritesSideEffect
import antuere.how_are_you.presentation.favorites.state.FavoritesState
import antuere.how_are_you.util.ContainerHostPlus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dayRepository: DayRepository,
) : ContainerHostPlus<FavoritesState, FavoritesSideEffect, FavoritesIntent>, ViewModel() {

    override val container: Container<FavoritesState, FavoritesSideEffect> =
        container(FavoritesState.LoadingShimmer())

    init {
        getFavoritesDays()
    }

    override fun onIntent(intent: FavoritesIntent) = intent {
        when (intent) {
            is FavoritesIntent.DayClicked -> {
                postSideEffect(
                    FavoritesSideEffect.NavigationToDayDetail(
                        dayId = intent.day.dayId
                    )
                )
            }
            is FavoritesIntent.DayLongClicked -> {
                val uiDialog = UIDialog(
                    title = R.string.dialog_delete_title,
                    desc = R.string.dialog_delete_desc,
                    icon = R.drawable.ic_delete_black,
                    positiveButton = UIDialog.UiButton(
                        text = R.string.yes,
                        onClick = {
                            deleteDay(intent.day.dayId)
                        }),
                    negativeButton = UIDialog.UiButton(text = R.string.no)
                )
                postSideEffect(FavoritesSideEffect.Dialog(uiDialog))
            }
        }
    }

    private fun getFavoritesDays() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.getFavoritesDays().collectLatest { favDays ->

                if (favDays.isEmpty()) {
                    reduce {
                        FavoritesState.Empty()
                    }
                } else {
                    reduce {
                        FavoritesState.Loaded(dayList = favDays)
                    }
                }
            }
        }
    }

    private fun deleteDay(dayId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.deleteDay(dayId)
        }
    }
}