package com.example.zeroapp.presentation.base.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController

@Composable
fun NavController.navigateToDayDetail(): (Long) -> Unit = remember {
    { this.navigate(Screen.Detail.route + "/$it") }
}