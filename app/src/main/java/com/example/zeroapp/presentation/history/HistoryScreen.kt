package com.example.zeroapp.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener

@Composable
fun HistoryScreen(
//    navController: NavController,
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    val dialogListener = UIDialogListener(LocalContext.current, historyViewModel)

//    val datePickerListener = UIDatePickerListener(requireActivity().supportFragmentManager, viewModel)

}