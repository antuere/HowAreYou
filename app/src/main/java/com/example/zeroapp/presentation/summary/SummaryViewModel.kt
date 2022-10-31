package com.example.zeroapp.presentation.summary

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.dto.Quote
import antuere.domain.usecases.GetDayQuoteUseCase
import antuere.domain.usecases.GetDaysByLimitUseCase
import antuere.domain.usecases.UpdateLastDayUseCase
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
import com.example.zeroapp.util.MyAnalystForSummary
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
    private val getDayQuoteUseCase: GetDayQuoteUseCase,
    private val getDaysByLimitUseCase: GetDaysByLimitUseCase,
    private val sharedPreferences: SharedPreferences,
    private val myAnalystForSummary: MyAnalystForSummary
) :
    ViewModel(), IUIDialogAction {

    companion object {
        private const val AUTHOR_PREF = "author"
        private const val TEXT_PREF = "text"
    }

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _lastDay = MutableLiveData<Day?>()
    val lastDay: LiveData<Day?>
        get() = _lastDay

    private var _dayQuote = MutableLiveData<Quote?>()
    val dayQuote: LiveData<Quote?>
        get() = _dayQuote

    private val _fabButtonState = MutableLiveData<FabButtonState>(FabButtonState.Add)
    val fabButtonState: LiveData<FabButtonState>
        get() = _fabButtonState

    private var _wishText = MutableLiveData<String>()
    val wishText: LiveData<String>
        get() = _wishText

    private var _daysForCheck = MutableLiveData<List<Day>>()

    init {
        getSavedDayQuote()
        getDayQuoteByFireBase()
        updateInfo()
        checkLastFiveDays()
    }

    fun updateInfo() {
        viewModelScope.launch {

            _lastDay.value = updateLastDayUseCase.invoke(Unit)

            if (TimeUtility.format(Date()) == (_lastDay.value?.dateString ?: "show add button")) {

                _fabButtonState.value = FabButtonState.Smile(lastDay.value?.imageId!!)
                _wishText.value =
                    myAnalystForSummary.getWishStringForSummary(lastDay.value?.imageId!!)

            } else {

                _fabButtonState.value = FabButtonState.Add
                _wishText.value =
                    myAnalystForSummary.getWishStringForSummary(MyAnalystForSummary.DEFAULT_WISH)

            }
        }
    }


    private fun getDayQuoteByFireBase() {
        viewModelScope.launch {
            _dayQuote.value = getDayQuoteUseCase.invoke(Unit)
        }
    }

    private fun getSavedDayQuote() {
        val sharedQuoteText = sharedPreferences.getString(TEXT_PREF, null) ?: ""
        val sharedQuoteAuthor = sharedPreferences.getString(AUTHOR_PREF, null) ?: ""

        _dayQuote.value = Quote(sharedQuoteText, sharedQuoteAuthor)
    }

    fun saveQuote(text: String, author: String) {
        sharedPreferences.edit().apply {
            putString(TEXT_PREF, text)
            putString(AUTHOR_PREF, author)
            apply()
        }
    }


    private fun checkLastFiveDays() {
        viewModelScope.launch {
            getLastFiveDays()
            delay(300)

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
                            _uiDialog.value = null
                        })
                )
            }
        }

    }

    private fun getLastFiveDays() {
        viewModelScope.launch {
            getDaysByLimitUseCase.invoke(5).collectLatest {
                _daysForCheck.postValue(it)
            }
        }
    }


}