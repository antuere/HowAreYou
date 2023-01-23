package com.example.zeroapp.presentation.detail.state

sealed interface DetailState {

    object Loading : DetailState

    data class Loaded(
        val imageResId: Int,
        val dayText: String,
        val dateString: String,
        val isFavorite: Boolean
    ) : DetailState
}
