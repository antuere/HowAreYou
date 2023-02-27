package antuere.how_are_you.presentation.screens.home

import androidx.lifecycle.viewModelScope
import antuere.domain.repository.DayRepository
import antuere.domain.repository.QuoteRepository
import antuere.domain.repository.SettingsRepository
import antuere.domain.util.TimeUtility
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.home.state.FabButtonState
import antuere.how_are_you.presentation.screens.home.state.HomeIntent
import antuere.how_are_you.presentation.screens.home.state.HomeSideEffect
import antuere.how_are_you.presentation.screens.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val quoteRepository: QuoteRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModelMvi<HomeState, HomeSideEffect, HomeIntent>() {

    override val container: Container<HomeState, HomeSideEffect> =
        container(HomeState.Loading)

    private var isShowWorriedDialogSetting = true

    private var _isShowSplash = MutableStateFlow(true)
    val isShowSplash: StateFlow<Boolean>
        get() = _isShowSplash

    init {
        updateDayQuoteByRemote()
        checkLastFiveDays()
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.FabClicked -> {
                val currentFabState = (state as HomeState.Loaded).fabButtonState
                when (currentFabState) {
                    FabButtonState.Add -> {
                        sideEffect(HomeSideEffect.NavigateToAddDay)
                    }
                    is FabButtonState.Smile -> {
                        sideEffect(
                            HomeSideEffect.NavigateToDayDetail(
                                dayId = currentFabState.dayId
                            )
                        )
                    }
                }
            }
            HomeIntent.CatsClicked -> sideEffect(HomeSideEffect.NavigateToCats)
            HomeIntent.FavoritesClicked -> sideEffect(HomeSideEffect.NavigateToFavorites)
            HomeIntent.HelpForYouClicked -> sideEffect(HomeSideEffect.NavigateToHelpForYou)
            HomeIntent.MentalTipsClicked -> sideEffect(HomeSideEffect.NavigateToMentalTips)
        }
    }

    private fun getSavedData() {
        viewModelScope.launch(Dispatchers.IO) {
            val savedQuote = quoteRepository.getDayQuoteLocal()
            if (_isShowSplash.value) _isShowSplash.value = false

            val currentDayTimeStamp = TimeUtility.parseCurrentTime().time

            combine(
                dayRepository.getDayById(currentDayTimeStamp),
                settingsRepository.getWorriedDialogSetting()
            ) { day, isEnableWorriedSetting ->

                isShowWorriedDialogSetting = isEnableWorriedSetting

                if (day == null) {
                    updateState {
                        HomeState.Loaded(
                            quoteText = savedQuote.text,
                            quoteAuthor = savedQuote.author,
                            wishText = HelperForHome.getWishStringForSummary(HelperForHome.DEFAULT_WISH),
                            fabButtonState = FabButtonState.Add
                        )
                    }
                } else {
                    updateState {
                        HomeState.Loaded(
                            quoteText = savedQuote.text,
                            quoteAuthor = savedQuote.author,
                            wishText = HelperForHome.getWishStringForSummary(day.imageResId),
                            fabButtonState = FabButtonState.Smile(
                                imageId = day.imageResId,
                                dayId = day.dayId
                            )
                        )
                    }
                }
            }.collect()
        }
    }

    private fun updateDayQuoteByRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            _isShowSplash.value = !quoteRepository.updateQuoteRemote()
            getSavedData()
        }
    }

    private fun checkLastFiveDays() {
        viewModelScope.launch(Dispatchers.IO) {
            val lastFiveDays = dayRepository.getDaysByLimit(5).first()
            delay(500)

            val isNeedShowWorriedDialog =
                HelperForHome.isShowWarningForSummary(lastFiveDays)

            if (isNeedShowWorriedDialog && isShowWorriedDialogSetting) {
                val dialog = UIDialog(
                    title = R.string.dialog_warning_title,
                    desc = R.string.dialog_warning_desc,
                    icon = R.drawable.ic_warning_dialog,
                    positiveButton = UIDialog.UiButton(
                        text = R.string.dialog_warning_positive,
                        onClick = {}),
                    negativeButton = UIDialog.UiButton(
                        text = R.string.dialog_warning_negative,
                        onClick = {
                            sideEffect(
                                HomeSideEffect.Snackbar(
                                    message = UiText.StringResource(R.string.snack_bar_warning_negative)
                                )
                            )
                        }),
//                    neutralButton = UIDialog.UiButton(
//                        text = R.string.dialog_warning_neutral,
//                        onClick = {
//                            notShowWorriedDialog()
//                            _uiDialog.value = null
//                        }
//                    )
                )

                sideEffect(HomeSideEffect.Dialog(dialog))
            }
        }
    }
}
