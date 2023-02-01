package com.example.zeroapp.presentation.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getLastDayUseCase: GetLastDayUseCase,
    private val updDayQuoteByRemoteUseCase: UpdDayQuoteByRemoteUseCase,
    private val getDayQuoteLocalUseCase: GetDayQuoteLocalUseCase,
    private val getDaysByLimitUseCase: GetDaysByLimitUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val myAnalystForSummary: MyAnalystForSummary
) :
    ViewModel(), IUIDialogAction {

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _lastDay = MutableLiveData<Day?>()
    val lastDay: LiveData<Day?>
        get() = _lastDay

    private var _dayQuote = MutableLiveData<Quote?>()
    val dayQuote: LiveData<Quote?>
        get() = _dayQuote

    private var _fabButtonState = MutableLiveData<FabButtonState>(FabButtonState.Add)
    val fabButtonState: LiveData<FabButtonState>
        get() = _fabButtonState

    private var _wishText = MutableLiveData<String>()
    val wishText: LiveData<String>
        get() = _wishText

    private var _daysForCheck = MutableLiveData<List<Day>>()

    private var _isShowSnackBar = MutableLiveData<Boolean>()
    val isShowSnackBar: LiveData<Boolean>
        get() = _isShowSnackBar

    private var _isShowSplash = MutableLiveData(true)
    val isShowSplash: LiveData<Boolean>
        get() = _isShowSplash

    private var _settings = MutableLiveData<Settings?>()
    val settings: LiveData<Settings?>
        get() = _settings


    init {
        updateDayQuoteByRemote()
        getSettings()
        getLastDay()
        checkLastFiveDays()
    }

    private fun getLastDay() {
        viewModelScope.launch(Dispatchers.IO) {
            getLastDayUseCase(Unit).collectLatest {
                _lastDay.postValue(it)
                delay(100)

                checkDayTime()
            }
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            getSettingsUseCase(Unit).collectLatest {
                _settings.postValue(it)
            }
        }
    }

    private fun getSavedDayQuote() {
        viewModelScope.launch {
            _dayQuote.value = getDayQuoteLocalUseCase(Unit)

            if (_isShowSplash.value == true) _isShowSplash.value = false
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
                _daysForCheck.postValue(it)
            }
        }
    }

    private fun checkLastFiveDays() {
        viewModelScope.launch {
            getLastFiveDays()
            delay(500)

            val result =
                myAnalystForSummary.isShowWarningForSummary(_daysForCheck.value ?: emptyList())

            if (result && _settings.value!!.isShowWorriedDialog) {
                _uiDialog.value = UIDialog(
                    title = R.string.dialog_warning_title,
                    desc = R.string.dialog_warning_desc,
                    icon = R.drawable.ic_warning_dialog,
                    positiveButton = UIDialog.UiButton(
                        text = R.string.dialog_warning_positive,
                        onClick = {
                            _uiDialog.value = null
                        }),
                    negativeButton = UIDialog.UiButton(
                        text = R.string.dialog_warning_negative,
                        onClick = {
                            _isShowSnackBar.value = true
                            _uiDialog.value = null
                        }),
                    neutralButton = UIDialog.UiButton(
                        text = R.string.dialog_warning_neutral,
                        onClick = {
                            notShowWorriedDialog()
                            _uiDialog.value = null
                        }
                    )
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

    private fun checkDayTime() {
        if (TimeUtility.format(Date()) == (_lastDay.value?.dateString ?: "show add button")) {
            _fabButtonState.postValue(FabButtonState.Smile(_lastDay.value?.imageResId!!))
            _wishText.postValue(
                myAnalystForSummary.getWishStringForSummary(_lastDay.value?.imageResId!!)
            )

        } else {
            _fabButtonState.postValue(FabButtonState.Add)
            _wishText.postValue(
                myAnalystForSummary.getWishStringForSummary(MyAnalystForSummary.DEFAULT_WISH)
            )
        }
    }

    fun resetSnackBar() {
        _isShowSnackBar.value = false
    }
}
