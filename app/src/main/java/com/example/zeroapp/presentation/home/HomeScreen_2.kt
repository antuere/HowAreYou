package com.example.zeroapp.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val dialogListener = UIDialogListener(LocalContext.current, homeViewModel)


}