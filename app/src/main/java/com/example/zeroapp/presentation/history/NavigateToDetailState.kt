package com.example.zeroapp.presentation.history

import androidx.navigation.fragment.FragmentNavigator

data class NavigateToDetailState(
    val extras: FragmentNavigator.Extras? = null,
    val dayId: Long? = null,
    var navigateToDetail: Boolean = false
)