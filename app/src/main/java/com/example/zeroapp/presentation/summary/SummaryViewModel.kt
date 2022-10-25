package com.example.zeroapp.presentation.summary

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.dto.Quote
import antuere.domain.usecases.GetDayQuoteUseCase
import antuere.domain.usecases.UpdateLastDayUseCase
import antuere.domain.util.TimeUtility
import com.example.zeroapp.util.WishAnalyzer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val updateLastDayUseCase: UpdateLastDayUseCase,
    private val getDayQuoteUseCase: GetDayQuoteUseCase,
    private val sharedPreferences: SharedPreferences,
    private val wishAnalyzer: WishAnalyzer
) :
    ViewModel() {

    companion object {
        private const val AUTHOR_PREF = "author"
        private const val TEXT_PREF = "text"
    }

    private var _lastDay = MutableLiveData<Day?>()
    val lastDay: LiveData<Day?>
        get() = _lastDay

    private var _dayQuote = MutableLiveData<Quote?>()
    val dayQuote: LiveData<Quote?>
        get() = _dayQuote

    private val _hideAddButton = MutableLiveData<HideAddButtonState>(HideAddButtonState.Add)
    val hideAddButton: LiveData<HideAddButtonState>
        get() = _hideAddButton

    private var _wishText = MutableLiveData<String>()
    val wishText: LiveData<String>
        get() = _wishText

    init {
        getSavedDayQuote()
        getDayQuoteByFireBase()
        updateInfo()
    }

    fun updateInfo() {
        viewModelScope.launch {

            _lastDay.value = updateLastDayUseCase.invoke(Unit)

            if (TimeUtility.format(Date()) == (_lastDay.value?.dateString ?: "show")) {

                _hideAddButton.value = HideAddButtonState.Smile(lastDay.value?.imageId!!)

                _wishText.value = wishAnalyzer.getWishString(lastDay.value?.imageId!!)

            } else {

                _hideAddButton.value = HideAddButtonState.Add
                _wishText.value = wishAnalyzer.getWishString(-1)

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

}