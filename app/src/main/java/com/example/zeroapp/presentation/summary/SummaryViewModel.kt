package com.example.zeroapp.presentation.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.dto.Quote
import antuere.domain.usecases.*
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
import com.example.zeroapp.util.SmileProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val updateLastDayUseCase: UpdateLastDayUseCase,
    private val getDayQuoteRemoteUseCase: GetDayQuoteRemoteUseCase,
    private val getDayQuoteLocalUseCase: GetDayQuoteLocalUseCase,
    private val saveDayQuoteLocalUseCase: SaveDayQuoteLocalUseCase,
    private val getDaysByLimitUseCase: GetDaysByLimitUseCase,
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

    init {
        getSavedDayQuote()
        getDayQuoteByFireBase()
        updateInfo()
        checkLastFiveDays()
    }

    fun updateInfo() {
        viewModelScope.launch {

            _lastDay.value = updateLastDayUseCase(Unit)

            if (TimeUtility.format(Date()) == (_lastDay.value?.dateString ?: "show add button")) {

                val resId = SmileProvider.getSmileImageByName(_lastDay.value?.imageName ?: "none")
                _fabButtonState.value = FabButtonState.Smile(resId)
                _wishText.value =
                    myAnalystForSummary.getWishStringForSummary(resId)

            } else {

                _fabButtonState.value = FabButtonState.Add
                _wishText.value =
                    myAnalystForSummary.getWishStringForSummary(MyAnalystForSummary.DEFAULT_WISH)

            }
        }
    }


    private fun getDayQuoteByFireBase() {
        viewModelScope.launch {
            _dayQuote.value = getDayQuoteRemoteUseCase(Unit)
        }
    }

    private fun getSavedDayQuote() {
        viewModelScope.launch {
            _dayQuote.value = getDayQuoteLocalUseCase(Unit)
        }
    }

    fun saveQuote(text: String, author: String) {
        viewModelScope.launch {
            val quote = Quote(text, author)
            saveDayQuoteLocalUseCase(quote)
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

            if (result) {
                _uiDialog.value = UIDialog(
                    title = R.string.dialog_warning_title,
                    desc = R.string.dialog_warning_message,
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
                        })
                )
            }
        }
    }

    fun resetSnackBar() {
        _isShowSnackBar.value = false
    }
}
