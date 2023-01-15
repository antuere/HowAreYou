package com.example.zeroapp.presentation.history

import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import antuere.domain.usecases.days_entities.DeleteDayUseCase
import antuere.domain.usecases.days_entities.GetAllDaysUseCase
import antuere.domain.usecases.days_entities.GetCertainDaysUseCase
import antuere.domain.usecases.days_entities.GetSelectedDaysUseCase
import antuere.domain.usecases.user_settings.GetToggleBtnStateUseCase
import antuere.domain.usecases.user_settings.SaveToggleBtnUseCase
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.base.ui_text.UiText
import com.example.zeroapp.presentation.history.state.HistorySideEffect
import com.example.zeroapp.presentation.history.state.HistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllDaysUseCase: GetAllDaysUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val getSelectedDaysUseCase: GetSelectedDaysUseCase,
    private val getCertainDaysUseCase: GetCertainDaysUseCase,
    private val getToggleBtnStateUseCase: GetToggleBtnStateUseCase,
    private val saveToggleBtnUseCase: SaveToggleBtnUseCase,
) : ContainerHost<HistoryState, HistorySideEffect>, ViewModel() {

    override val container: Container<HistoryState, HistorySideEffect> =
        container(HistoryState.Loading.Default)

    private var currentJob: JobType? = null

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

    fun onClickLongDay(day: Day) {
        onClickLongSmile(day)
    }

    private fun deleteDay(dayId: Long) {
        viewModelScope.launch {
            deleteDayUseCase(dayId)
        }
    }

    private fun onClickLongSmile(day: Day) = intent {
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

    fun onDaysSelected(pair: Pair<Long, Long>) = intent {
        postSideEffect(HistorySideEffect.AnimationHistoryHeader)

        currentJob?.job?.cancel()
        currentJob = JobType.Filter(viewModelScope.launch {
            getSelectedDaysUseCase(pair).cancellable().collectLatest { days ->
                reduce {
                    if (days.isEmpty()) {
                        return@reduce HistoryState.Empty.FromFilter(
                            message = UiText.StringResource(R.string.no_days_filter)
                        )
                    } else {
                        return@reduce HistoryState.Loaded.FilterSelected(days = days)
                    }
                }
            }
        })
    }

    private fun checkedCurrentMonthButton() = intent {
        if (currentJob !is JobType.Month) {
            currentJob?.job?.cancel()

            currentJob = JobType.Month(viewModelScope.launch {
                getCertainDaysUseCase(TimeUtility.getCurrentMonthTime()).cancellable()
                    .collectLatest { days ->
                        reduce {
                            val toggleBtnState = ToggleBtnState.CURRENT_MONTH

                            if (days.isEmpty()) {
                                return@reduce HistoryState.Empty.FromToggleGroup(
                                    message = UiText.StringResource(R.string.no_days_month),
                                    toggleBtnState = toggleBtnState
                                )
                            } else {
                                return@reduce HistoryState.Loaded.Default(
                                    days = days,
                                    toggleBtnState = toggleBtnState,
                                    cellsAmount = 3
                                )
                            }
                        }
                    }
            })
        }
    }

    private fun checkedLastWeekButton() = intent {
        if (currentJob !is JobType.Week) {
            currentJob?.job?.cancel()

            currentJob = JobType.Week(viewModelScope.launch {
                getCertainDaysUseCase(TimeUtility.getCurrentWeekTime()).cancellable()
                    .collectLatest { days ->
                        reduce {
                            val toggleBtnState = ToggleBtnState.LAST_WEEK

                            if (days.isEmpty()) {
                                return@reduce HistoryState.Empty.FromToggleGroup(
                                    message = UiText.StringResource(R.string.no_days_week),
                                    toggleBtnState = toggleBtnState
                                )
                            } else {
                                return@reduce HistoryState.Loaded.Default(
                                    days = days,
                                    toggleBtnState = toggleBtnState,
                                    cellsAmount = 2
                                )
                            }
                        }
                    }
            })
        }
    }

    private fun checkedAllDaysButton() = intent {
        if (currentJob !is JobType.AllDays) {
            currentJob?.job?.cancel()

            currentJob = JobType.AllDays(viewModelScope.launch {
                getAllDaysUseCase(Unit).cancellable().collectLatest { days ->
                    reduce {
                        if (days.isEmpty()) return@reduce HistoryState.Empty.NoEntriesYet(
                            UiText.StringResource(R.string.no_days_all)
                        )
                        else {
                            return@reduce HistoryState.Loaded.Default(
                                days = days,
                                toggleBtnState = ToggleBtnState.ALL_DAYS,
                                cellsAmount = 4
                            )
                        }
                    }
                }
            })
        }
    }

    private fun saveToggleButtonState(toggleBtnState: ToggleBtnState) = intent {
        viewModelScope.launch {
            saveToggleBtnUseCase(toggleBtnState)
        }
    }

    private fun getToggleButtonState() {
        viewModelScope.launch(Dispatchers.IO) {
            val savedState = getToggleBtnStateUseCase(Unit).first()
            getDaysByToggleState(state = savedState)
        }
    }

    fun onClickCheckedItem(btnState: ToggleBtnState) = intent {
        getDaysByToggleState(btnState)
        saveToggleButtonState(btnState)
    }

    private fun getDaysByToggleState(state: ToggleBtnState) = intent {
        postSideEffect(HistorySideEffect.AnimationHistoryHeader)
        when (state) {
            ToggleBtnState.ALL_DAYS -> {
                reduce {
                    HistoryState.Loading.ItemsShimmer(
                        cellsAmount = 4,
                        toggleBtnState = state
                    )
                }
                checkedAllDaysButton()
            }
            ToggleBtnState.CURRENT_MONTH -> {
                reduce {
                    HistoryState.Loading.ItemsShimmer(
                        cellsAmount = 3,
                        toggleBtnState = state
                    )
                }
                checkedCurrentMonthButton()
            }
            ToggleBtnState.LAST_WEEK -> {
                reduce {
                    HistoryState.Loading.ItemsShimmer(
                        cellsAmount = 2,
                        toggleBtnState = state
                    )
                }
                checkedLastWeekButton()
            }
        }
    }
}