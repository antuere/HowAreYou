package com.example.zeroapp.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import javax.inject.Inject

@Composable
fun HistoryScreen(
//    navController: NavController,
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel = hiltViewModel(),
    myAnalystForHistory: MyAnalystForHistory
) {
//    val datePickerListener = UIDatePickerListener(requireActivity().supportFragmentManager, viewModel)
    val uiDialog by historyViewModel.uiDialog.collectAsState()
    val listDays by historyViewModel.listDays.collectAsState()
    val isFilterSelected by historyViewModel.isFilterSelected.collectAsState()
    val toggleBtnState by historyViewModel.toggleBtnState.collectAsState()
    val navigateToDetailState by historyViewModel.navigateToDetailState.collectAsState()

    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {

    }
}