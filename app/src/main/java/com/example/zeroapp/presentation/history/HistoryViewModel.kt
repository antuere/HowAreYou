package com.example.zeroapp.presentation.history

import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.DayRepository
import antuere.domain.repository.ToggleBtnRepository
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.base.ui_text.UiText
import com.example.zeroapp.presentation.history.state.FilterState
import com.example.zeroapp.presentation.history.state.HistorySideEffect
import com.example.zeroapp.presentation.history.state.HistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val toggleBtnRepository: ToggleBtnRepository
) : ContainerHost<HistoryState, HistorySideEffect>, ViewModel() {

    override val container: Container<HistoryState, HistorySideEffect> =
        container(HistoryState.LoadingShimmer())

    private val filterState: MutableStateFlow<FilterState> =
        MutableStateFlow(FilterState.Disabled(ToggleBtnState.ALL_DAYS))

    init {
        getToggleButtonState()
    }

    fun onClickDay(day: Day) = intent {
        postSideEffect(
            HistorySideEffect.NavigationToDayDetail(
                dayId = day.dayId
            )
        )
    }

    fun onClickLongDay(day: Day) = intent {
        val uiDialog = UIDialog(
            title = R.string.dialog_delete_title,
            desc = R.string.dialog_delete_desc,
            icon = R.drawable.ic_delete_black,
            positiveButton = UIDialog.UiButton(
                text = R.string.yes,
                onClick = {
                    deleteDay(day.dayId)
                }),
            negativeButton = UIDialog.UiButton(text = R.string.no)
        )
        postSideEffect(HistorySideEffect.Dialog(uiDialog))
    }

    fun onClickCheckedItem(btnState: ToggleBtnState) = intent {

        filterState.update {
            FilterState.Disabled(btnState)
        }
        saveToggleButtonState(btnState)
        postSideEffect(HistorySideEffect.AnimationHistoryHeader)
    }

    fun onDaysSelected(startDate: LocalDate, endDate: LocalDate) = intent {

        postSideEffect(HistorySideEffect.AnimationHistoryHeader)
        filterState.update {
            FilterState.Activated(startDate, endDate)
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
                            reduce {
                                HistoryState.LoadingShimmer(
                                    cellsAmount = 2,
                                    aspectRatioForItem = 2F,
                                    toggleBtnState = filterState.toggleBtnState
                                )
                            }
                            dayRepository.getCertainDays(TimeUtility.getCurrentWeekTime())
                        }
                        ToggleBtnState.CURRENT_MONTH -> {
                            reduce {
                                HistoryState.LoadingShimmer(
                                    cellsAmount = 3,
                                    aspectRatioForItem = 1F,
                                    toggleBtnState = filterState.toggleBtnState
                                )
                            }
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