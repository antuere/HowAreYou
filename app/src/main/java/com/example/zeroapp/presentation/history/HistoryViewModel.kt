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
import kotlinx.coroutines.delay
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
        container(HistoryState.Loading)

//    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
//    val uiDialog: StateFlow<UIDialog?>
//        get() = _uiDialog

//    private var _listDays = MutableStateFlow<List<Day>?>(null)
//    val listDays: StateFlow<List<Day>?>
//        get() = _listDays

//    private var _navigateToDetailState = MutableStateFlow<NavigateToDetailState?>(null)
//    val navigateToDetailState: StateFlow<NavigateToDetailState?>
//        get() = _navigateToDetailState

//    private var _toggleBtnState = MutableStateFlow(ToggleBtnState.ALL_DAYS)
//    val toggleBtnState: StateFlow<ToggleBtnState>
//        get() = _toggleBtnState
//
//    private var _cellsAmount = MutableStateFlow(4)
//    val cellsAmount: StateFlow<Int>
//        get() = _cellsAmount

//    private var _isShowAnimation = MutableStateFlow(false)
//    val isShowAnimation: StateFlow<Boolean>
//        get() = _isShowAnimation

    private var _currentJob: JobType? = null

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

//    fun doneNavigateToDetail() {
//        _navigateToDetailState.value!!.navigateToDetail = false
//    }

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
            negativeButton = UIDialog.UiButton(
                text = R.string.no,
                onClick = {})
        )
        postSideEffect(HistorySideEffect.Dialog(uiDialog))
    }

    fun onDaysSelected(pair: Pair<Long, Long>) = intent {
        postSideEffect(HistorySideEffect.AnimationToggleGroup)

        _currentJob?.job?.cancel()
        _currentJob = JobType.Filter(viewModelScope.launch {
            getSelectedDaysUseCase(pair).cancellable().collectLatest { days ->
                reduce {
                    if (days.isEmpty()) {
                        return@reduce HistoryState.Empty.FromFilter(
                            message = UiText.StringResource(R.string.no_days_filter)
                        )
                    } else {
                        return@reduce HistoryState.Loaded.FilterSelected(
                            days = days,
                            cellsAmount = 3
                        )
                    }
                }
            }
        })
    }

    private fun checkedCurrentMonthButton() = intent {
        if (_currentJob !is JobType.Month) {
            _currentJob?.job?.cancel()

            _currentJob = JobType.Month(viewModelScope.launch {
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
        if (_currentJob !is JobType.Week) {
            _currentJob?.job?.cancel()

            _currentJob = JobType.Week(viewModelScope.launch {
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
        if (_currentJob !is JobType.AllDays) {
            _currentJob?.job?.cancel()

            _currentJob = JobType.AllDays(viewModelScope.launch {
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
            delay(150)
            saveToggleBtnUseCase(toggleBtnState)
        }
    }

    private fun getToggleButtonState() {
        viewModelScope.launch(Dispatchers.IO) {
            getToggleBtnStateUseCase(Unit).collectLatest {
                onClickCheckedItem(it)
            }
        }
    }

    fun onClickCheckedItem(state: ToggleBtnState) = intent {
        postSideEffect(HistorySideEffect.AnimationToggleGroup)

        when (state) {
            ToggleBtnState.ALL_DAYS -> {
                checkedAllDaysButton()
            }
            ToggleBtnState.CURRENT_MONTH -> {
                checkedCurrentMonthButton()
            }
            ToggleBtnState.LAST_WEEK -> {
                checkedLastWeekButton()
            }
        }
        saveToggleButtonState(state)
    }

//    fun resetIsShowAnimation() {
//        _isShowAnimation.value = false
//    }
}


