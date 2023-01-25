package antuere.how_are_you.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.dto.Quote
import antuere.domain.dto.Settings
import antuere.domain.repository.DayRepository
import antuere.domain.repository.QuoteRepository
import antuere.domain.repository.SettingsRepository
import antuere.domain.util.TimeUtility
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val quoteRepository: QuoteRepository,
    private val settingsRepository: SettingsRepository
) :
    ViewModel() {

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _lastDay = MutableStateFlow<Day?>(null)

    private var _dayQuote = MutableStateFlow<Quote?>(null)
    val dayQuote: StateFlow<Quote?>
        get() = _dayQuote

    private var _fabButtonState = MutableStateFlow<FabButtonState>(FabButtonState.Add)
    val fabButtonState: StateFlow<FabButtonState>
        get() = _fabButtonState

    private var _wishText =
        MutableStateFlow(MyAnalystForHome.getWishStringForSummary(MyAnalystForHome.DEFAULT_WISH))
    val wishText: StateFlow<UiText>
        get() = _wishText

    private var _isShowMessage = MutableStateFlow(false)
    val isShowMessage: StateFlow<Boolean>
        get() = _isShowMessage

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

            dayRepository.getLastDay().collectLatest {
                _lastDay.value = it
                delay(100)

                checkDayTime()
            }
        }
    }

    private fun getSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.getSettings().collectLatest {
                _settings.value = it
            }
        }
    }

    private fun getSavedDayQuote() {
        viewModelScope.launch(Dispatchers.IO) {
            _dayQuote.value = quoteRepository.getDayQuoteLocal()

            if (_isShowSplash.value) _isShowSplash.value = false
        }
    }

    private fun updateDayQuoteByRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            _isShowSplash.value = !quoteRepository.updateQuoteRemote()
            getSavedDayQuote()
        }
    }

    private fun getLastFiveDays() {
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.getDaysByLimit(5).collectLatest {
                _daysForCheck.value = it
            }
        }
    }

    private fun checkLastFiveDays() {
        viewModelScope.launch {
            getLastFiveDays()
            delay(500)

            val isShowWorriedDialog =
                MyAnalystForHome.isShowWarningForSummary(_daysForCheck.value)

            if (isShowWorriedDialog && _settings.value!!.isShowWorriedDialog) {
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
                            _isShowMessage.value = true
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
        viewModelScope.launch(Dispatchers.IO) {
            _settings.value!!.isShowWorriedDialog = false
            settingsRepository.saveSettings(_settings.value!!)
        }
    }

    private fun checkDayTime() {
        if (TimeUtility.formatDate(Date()) == (_lastDay.value?.dateString ?: "show add button")) {
            val currentDay = _lastDay.value!!
            _fabButtonState.value =
                FabButtonState.Smile(imageId = currentDay.imageResId, dayId = currentDay.dayId)
            _wishText.value =
                MyAnalystForHome.getWishStringForSummary(currentDay.imageResId)
        } else {
            _fabButtonState.value = FabButtonState.Add
            _wishText.value =
                MyAnalystForHome.getWishStringForSummary(MyAnalystForHome.DEFAULT_WISH)
        }
    }

    fun resetMessage() {
        _isShowMessage.value = false
    }
}
