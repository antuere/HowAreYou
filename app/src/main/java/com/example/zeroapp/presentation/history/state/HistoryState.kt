package com.example.zeroapp.presentation.history.state

import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_text.UiText

sealed interface HistoryState {

    sealed interface Loading : HistoryState {
        object Default : Loading
        data class ItemsShimmer(
            val cellsAmount: Int,
            val toggleBtnState: ToggleBtnState,
            val dateTextPlug: UiText = UiText.StringResource(R.string.loading)
        ) : Loading
    }

    sealed class Empty(message: UiText) : HistoryState {
        class NoEntriesYet(val message: UiText) : Empty(message)
        class FromToggleGroup(val message: UiText, val toggleBtnState: ToggleBtnState) :
            Empty(message)

        class FromFilter(val message: UiText) : Empty(message)
    }

    sealed interface Loaded : HistoryState {

        data class Default(
            val days: List<Day>,
            val toggleBtnState: ToggleBtnState,
            val cellsAmount: Int
        ) : Loaded

        data class FilterSelected(
            val days: List<Day>,
            val cellsAmount: Int = 3
        ) : Loaded
    }
}
