package antuere.how_are_you.presentation.history

import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.DayRepository
import antuere.domain.repository.ToggleBtnRepository
import antuere.domain.util.TimeUtility
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.history.state.FilterState
import antuere.how_are_you.presentation.history.state.HistoryIntent
import antuere.how_are_you.presentation.history.state.HistorySideEffect
import antuere.how_are_you.presentation.history.state.HistoryState
import antuere.how_are_you.util.ContainerHostPlus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val toggleBtnRepository: ToggleBtnRepository,
) : ContainerHostPlus<HistoryState, HistorySideEffect, HistoryIntent>, ViewModel() {

    override val container: Container<HistoryState, HistorySideEffect> =
        container(HistoryState.LoadingShimmer())

    private val filterState: MutableStateFlow<FilterState> =
        MutableStateFlow(FilterState.Disabled(ToggleBtnState.ALL_DAYS))

    init {
        getToggleButtonState()
    }

    override fun onIntent(intent: HistoryIntent) = intent {
        when (intent) {
            is HistoryIntent.DayClicked -> {
                postSideEffect(
                    HistorySideEffect.NavigationToDayDetail(
                        dayId = intent.day.dayId
                    )
                )
            }
            is HistoryIntent.DayLongClicked -> {
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
                postSideEffect(HistorySideEffect.Dialog(uiDialog))
            }
            is HistoryIntent.DaysInFilterSelected -> {
                postSideEffect(HistorySideEffect.AnimationHistoryHeader)
                filterState.update {
                    FilterState.Activated(
                        firstDate = intent.startDate,
                        secondDate = intent.endDate
                    )
                }
            }
            is HistoryIntent.ToggleBtnChanged -> {
                filterState.update {
                    FilterState.Disabled(intent.toggleBtnState)
                }
                saveToggleButtonState(intent.toggleBtnState)
                postSideEffect(HistorySideEffect.AnimationHistoryHeader)
            }
        }
    }

    private fun getToggleButtonState() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            val savedToggleState = toggleBtnRepository.getToggleButtonState().first()

            filterState.update {
                FilterState.Disabled(savedToggleState)
            }
            subscribeOnDaysFlow()
        }
    }

    private fun subscribeOnDaysFlow() = intent {
        val daysFlow = filterState.flatMapLatest { filterState ->
            when (filterState) {
                is FilterState.Activated -> {
                    val startDateInSec = TimeUtility.getTimeInMilliseconds(filterState.firstDate)
                    val endDateInSec = TimeUtility.getTimeInMilliseconds(filterState.secondDate)

                    dayRepository.getSelectedDays(startDateInSec, endDateInSec)
                }
                is FilterState.Disabled -> {
                    when (filterState.toggleBtnState) {
                        ToggleBtnState.ALL_DAYS -> {
                            reduce {
                                HistoryState.LoadingShimmer(
                                    cellsAmount = 4,
                                    aspectRatioForItem = 3.5f / 4f,
                                    toggleBtnState = filterState.toggleBtnState
                                )
                            }
                            dayRepository.getAllDays()
                        }
                        ToggleBtnState.LAST_WEEK -> {
                            dayRepository.getCertainDays(TimeUtility.getCurrentWeekTime())
                        }
                        ToggleBtnState.CURRENT_MONTH -> {
                            dayRepository.getCertainDays(TimeUtility.getCurrentMonthTime())
                        }
                    }
                }
            }
        }

        daysFlow.collectLatest { days ->
            when (val filterState = filterState.first()) {
                is FilterState.Activated -> {
                    getDaysByFilter(days)
                }
                is FilterState.Disabled -> {
                    getDaysByToggleState(days, filterState.toggleBtnState)
                }
            }
        }
    }

    private fun getDaysByFilter(days: List<Day>) = intent {
        reduce {
            if (days.isEmpty()) {
                HistoryState.Empty.FromFilter(
                    message = UiText.StringResource(R.string.no_days_filter)
                )
            } else {
                HistoryState.Loaded.FilterSelected(
                    dayList = days,
                    textHeadline = HelperForHistory.getHeaderForHistory(days)
                )
            }
        }
    }

    private fun getDaysByToggleState(days: List<Day>, toggleState: ToggleBtnState) = intent {
        when (toggleState) {
            ToggleBtnState.ALL_DAYS -> {
                reduce {
                    if (days.isEmpty()) {
                        HistoryState.Empty.NoEntriesYet(
                            UiText.StringResource(R.string.no_days_all)
                        )
                    } else {
                        HistoryState.Loaded.Default(
                            dayList = days,
                            toggleBtnState = ToggleBtnState.ALL_DAYS,
                            cellsAmountForGrid = 4,
                            textHeadline = HelperForHistory.getHeaderForHistory(days)
                        )
                    }
                }
            }
            ToggleBtnState.LAST_WEEK -> {
                reduce {
                    if (days.isEmpty()) {
                        HistoryState.Empty.FromToggleGroup(
                            message = UiText.StringResource(R.string.no_days_week),
                            toggleBtnState = toggleState
                        )
                    } else {
                        HistoryState.Loaded.Default(
                            dayList = days,
                            toggleBtnState = toggleState,
                            cellsAmountForGrid = 2,
                            textHeadline = HelperForHistory.getHeaderForHistory(days)
                        )
                    }
                }
            }
            ToggleBtnState.CURRENT_MONTH -> {
                reduce {
                    if (days.isEmpty()) {
                        HistoryState.Empty.FromToggleGroup(
                            message = UiText.StringResource(R.string.no_days_month),
                            toggleBtnState = toggleState
                        )
                    } else {
                        HistoryState.Loaded.Default(
                            dayList = days,
                            toggleBtnState = toggleState,
                            cellsAmountForGrid = 3,
                            textHeadline = HelperForHistory.getHeaderForHistory(days)
                        )
                    }
                }
            }
        }
    }

    private fun saveToggleButtonState(toggleBtnState: ToggleBtnState) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleBtnRepository.saveToggleButtonState(toggleBtnState)
        }
    }

    private fun deleteDay(dayId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.deleteDay(dayId)
        }
    }
}