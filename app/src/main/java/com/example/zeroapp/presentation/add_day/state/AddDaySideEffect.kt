package com.example.zeroapp.presentation.add_day.state

sealed interface AddDaySideEffect {
    object NavigateUp : AddDaySideEffect
}