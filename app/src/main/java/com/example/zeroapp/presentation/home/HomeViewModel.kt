package com.example.zeroapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.dto.Quote
import antuere.domain.dto.Settings
import antuere.domain.usecases.day_quote.GetDayQuoteLocalUseCase
import antuere.domain.usecases.day_quote.UpdDayQuoteByRemoteUseCase
import antuere.domain.usecases.days_entities.GetDaysByLimitUseCase
import antuere.domain.usecases.days_entities.GetLastDayUseCase
import antuere.domain.usecases.user_settings.GetSettingsUseCase
import antuere.domain.usecases.user_settings.SaveSettingsUseCase
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialogCompose
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLastDayUseCase: GetLastDayUseCase,
    private val updDayQuoteByRemoteUseCase: UpdDayQuoteByRemoteUseCase,
    private val getDayQuoteLocalUseCase: GetDayQuoteLocalUseCase,
    private val getDaysByLimitUseCase: GetDaysByLimitUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val myAnalystForSummary: MyAnalystForHome
) :
    ViewModel() {

    private var _uiDialog = MutableStateFlow<UIDialogCompose?>(null)
    val uiDialog: StateFlow<UIDialogCompose?>
        get() = _uiDialog

    private var _lastDay = MutableStateFlow<Day?>(null)
    val lastDay: StateFlow<Day?>
        get() = _lastDay

    private var _dayQuote = MutableStateFlow<Quote?>(null)
    val dayQuote: StateFlow<Quote?>
        get() = _dayQuote

    private var _fabButtonState = MutableStateFlow<FabButtonState>(FabButtonState.Add)
    val fabButtonState: StateFlow<FabButtonState>
        get() = _fabButtonState

    private var _wishText =
        MutableStateFlow(myAnalystForSummary.getWishStringForSummary(MyAnalystForHome.DEFAULT_WISH))
    val wishText: StateFlow<String>
        get() = _wishText

    private var _isShowSnackBar = MutableStateFlow(false)
    val isShowSnackBar: StateFlow<Boolean>
        get() = _isShowSnackBar

    private var _isShowSplash = MutableStateFlow(true)
    val isShowSplash: StateFlow<Boolean>
        get() = _isShowSplash


    private var _settings = MutableStateFlow<Settings?>(null)
    private var _daysForCheck = MutableStateFlow<List<Day>>(emptyList())


    init {
        updateDayQuoteByRemote()
        getSettings()
        getLastDay()
        checkLastFiveDays()
    }

    private fun getLastDay() {
        viewModelScope.launch(Dispatchers.IO) {

            getLastDayUseCase(Unit).collectLatest {
                _lastDay.value = it
                delay(100)

                checkDayTime()
            }
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            getSettingsUseCase(Unit).collectLatest {
                _settings.value = it
            }
        }
    }

    private fun getSavedDayQuote() {
        viewModelScope.launch {
            _dayQuote.value = getDayQuoteLocalUseCase(Unit)

            if (_isShowSplash.value) _isShowSplash.value = false
        }
    }

    private fun updateDayQuoteByRemote() {
        viewModelScope.launch {
            _isShowSplash.value = !updDayQuoteByRemoteUseCase(Unit)

            getSavedDayQuote()
        }
    }

    private fun getLastFiveDays() {
        viewModelScope.launch {
            getDaysByLimitUseCase(5).collectLatest {
                _daysForCheck.value = it
            }
        }
    }

    private fun checkLastFiveDays() {
        viewModelScope.launch {
            getLastFiveDays()
            delay(500)

            val isShowWorriedDialog =
                myAnalystForSummary.isShowWarningForSummary(_daysForCheck.value)

            if (isShowWorriedDialog && _settings.value!!.isShowWorriedDialog) {
                _uiDialog.value = UIDialogCompose(
                    title = R.string.dialog_warning_title,
                    desc = R.string.dialog_warning_desc,
                    icon = R.drawable.ic_warning_dialog,
                    positiveButton = UIDialogCompose.UiButton(
                        text = R.string.dialog_warning_positive,
                        onClick = {
                            _uiDialog.value = null
                        }),
                    negativeButton = UIDialogCompose.UiButton(
                        text = R.string.dialog_warning_negative,
                        onClick = {
                            _isShowSnackBar.value = true
                            _uiDialog.value = null
                        }),
                    dismissAction = { _uiDialog.value = null }
//                    neutralButton = UIDialog.UiButton(
//                        text = R.string.dialog_warning_neutral,
//                        onClick = {
//                            notShowWorriedDialog()
//                            _uiDialog.value = null
//                        }
//                    )
                )
            }
        }
    }

    private fun notShowWorriedDialog() {
        viewModelScope.launch {
            _settings.value!!.isShowWorriedDialog = false
            saveSettingsUseCase(_settings.value!!)
        }
    }

    fun testDialog() {
        _uiDialog.value = UIDialogCompose(
            title = R.string.dialog_warning_title,
            desc = R.string.dialog_warning_desc,
            icon = R.drawable.ic_warning_dialog,
            positiveButton = UIDialogCompose.UiButton(
                text = R.string.dialog_warning_positive,
                onClick = {
                    _uiDialog.value = null
                }),
            negativeButton = UIDialogCompose.UiButton(
                text = R.string.dialog_warning_negative,
                onClick = {
                    _isShowSnackBar.value = true
                    _uiDialog.value = null
                })
        )
    }

    private fun checkDayTime() {
        if (TimeUtility.format(Date()) == (_lastDay.value?.dateString ?: "show add button")) {
            _fabButtonState.value = FabButtonState.Smile(_lastDay.value?.imageResId!!)
            _wishText.value =
                myAnalystForSummary.getWishStringForSummary(_lastDay.value?.imageResId!!)


        } else {
            _fabButtonState.value = FabButtonState.Add
            _wishText.value =
                myAnalystForSummary.getWishStringForSummary(MyAnalystForHome.DEFAULT_WISH)
        }
    }

    fun resetSnackBar() {
        _isShowSnackBar.value = false
    }
}
