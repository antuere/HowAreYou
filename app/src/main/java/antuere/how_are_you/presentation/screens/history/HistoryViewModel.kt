package antuere.how_are_you.presentation.screens.history

import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.DayRepository
import antuere.domain.repository.ToggleBtnRepository
import antuere.domain.util.TimeUtility
import antuere.domain.util.TimeUtility.convertFromUTC
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.history.state.FilterState
import antuere.how_are_you.presentation.screens.history.state.HistoryIntent
import antuere.how_are_you.presentation.screens.history.state.HistorySideEffect
import antuere.how_are_you.presentation.screens.history.state.HistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val toggleBtnRepository: ToggleBtnRepository,
) : ViewModelMvi<HistoryState, HistorySideEffect, HistoryIntent>() {


    override val container: Container<HistoryState, HistorySideEffect> =
        container(HistoryState.LoadingShimmer())

    private val filterState: MutableStateFlow<FilterState> =
        MutableStateFlow(FilterState.Disabled(ToggleBtnState.ALL_DAYS))

    private var isHasSavedDays = false
    private var savedToggleBtnState: ToggleBtnState? = null

    init {
        getToggleButtonState()
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.getLastDay().collectLatest {
                isHasSavedDays = it != null
            }
        }
    }

    override fun onIntent(intent: HistoryIntent) {
        when (intent) {
            is HistoryIntent.DayClicked -> {
                sideEffect(
                    HistorySideEffect.NavigationToDayDetail(
                        dayId = intent.day.dayId
                    )
                )
            }

            is HistoryIntent.DayLongClicked -> {
                val uiDialog = UIDialog(
                    title = R.string.dialog_delete_title,
                    desc = R.string.dialog_delete_desc,
                    icon = R.drawable.ic_delete,
                    positiveButton = UIDialog.UiButton(
                        text = R.string.yes,
                        onClick = {
                            deleteDay(intent.day.dayId)
                        }),
                    negativeButton = UIDialog.UiButton(text = R.string.no)
                )
                sideEffect(HistorySideEffect.Vibration)
                sideEffect(HistorySideEffect.Dialog(uiDialog))
            }

            is HistoryIntent.DaysInFilterSelected -> {
                sideEffect(HistorySideEffect.AnimationHistoryHeader)
                filterState.update {
                    FilterState.Activated(selectedDates = intent.selectedDates)
                }
            }

            is HistoryIntent.ToggleBtnChanged -> {
                saveToggleButtonState(intent.toggleBtnState)
                sideEffect(HistorySideEffect.AnimationHistoryHeader)
            }

            HistoryIntent.FilterCloseBtnClicked -> {
                filterState.update {
                    FilterState.Disabled(savedToggleBtnState ?: ToggleBtnState.CURRENT_MONTH)
                }
                sideEffect(HistorySideEffect.AnimationHistoryHeader)
            }
        }
    }

    private fun getToggleButtonState() {
        viewModelScope.launch(Dispatchers.IO) {
            toggleBtnRepository.getToggleButtonState().collectLatest { toggleBtnState ->
                filterState.update {
                    FilterState.Disabled(toggleBtnState)
                }
                savedToggleBtnState = toggleBtnState
                subscribeOnDaysFlow()
            }
        }
    }

    private suspend fun subscribeOnDaysFlow() {
        val daysFlow = filterState.flatMapLatest { filterState ->
            when (filterState) {
                is FilterState.Activated -> {
                    val firstTime = filterState.selectedDates.startInMillis.convertFromUTC()
                    val secondTime = filterState.selectedDates.endInMillis.convertFromUTC()
                    dayRepository.getSelectedDays(firstTime, secondTime)
                }

                is FilterState.Disabled -> {
                    when (filterState.toggleBtnState) {
                        ToggleBtnState.ALL_DAYS -> {
                            updateState {
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

    private fun getDaysByFilter(days: List<Day>) {
        updateState {
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

    private fun getDaysByToggleState(days: List<Day>, toggleState: ToggleBtnState) {
        when (toggleState) {
            ToggleBtnState.ALL_DAYS -> {
                updateState {
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
                updateState {
                    if (!isHasSavedDays && days.isEmpty()) {
                        return@updateState HistoryState.Empty.NoEntriesYet(
                            UiText.StringResource(R.string.no_days_all)
                        )
                    }
                    if (days.isEmpty()) {
                        return@updateState HistoryState.Empty.FromToggleGroup(
                            message = UiText.StringResource(R.string.no_days_week),
                            calendarImageRes = R.drawable.calendar_week,
                            toggleBtnState = toggleState
                        )
                    }
                    HistoryState.Loaded.Default(
                        dayList = days,
                        toggleBtnState = toggleState,
                        cellsAmountForGrid = 2,
                        textHeadline = HelperForHistory.getHeaderForHistory(days)
                    )
                }
            }

            ToggleBtnState.CURRENT_MONTH -> {
                updateState {
                    if (!isHasSavedDays && days.isEmpty()) {
                        return@updateState HistoryState.Empty.NoEntriesYet(
                            UiText.StringResource(R.string.no_days_all)
                        )
                    }
                    if (days.isEmpty()) {
                        return@updateState HistoryState.Empty.FromToggleGroup(
                            message = UiText.StringResource(R.string.no_days_month),
                            calendarImageRes = R.drawable.calendar_month,
                            toggleBtnState = toggleState
                        )
                    }
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