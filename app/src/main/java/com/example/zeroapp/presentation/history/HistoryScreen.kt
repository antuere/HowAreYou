package com.example.zeroapp.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.domain.dto.ToggleBtnState
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.base.ui_compose_components.DaysListItem
import com.example.zeroapp.presentation.history.ui_compose.HistoryHeaderText
import com.example.zeroapp.presentation.history.ui_compose.ToggleBtnGroup

@Composable
fun HistoryScreen(
    onNavigateToDetail : (Long) -> Unit,
    onComposing: (AppBarState, Boolean) -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel(),
    myAnalystForHistory: MyAnalystForHistory
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = R.string.history,
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = null
                        )
                    }
                }
            ),
            true
        )
    }

//    val datePickerListener = UIDatePickerListener(requireActivity().supportFragmentManager, viewModel)
    val uiDialog by historyViewModel.uiDialog.collectAsState()
    val listDays by historyViewModel.listDays.collectAsState()
    val isFilterSelected by historyViewModel.isFilterSelected.collectAsState()
    val toggleBtnState by historyViewModel.toggleBtnState.collectAsState()
    val navigateToDetailState by historyViewModel.navigateToDetailState.collectAsState()

    val cellsAmount = when (toggleBtnState) {
        ToggleBtnState.ALL_DAYS -> {
            historyViewModel.checkedAllDaysButton()
            4
        }
        ToggleBtnState.CURRENT_MONTH -> {
            historyViewModel.checkedCurrentMonthButton()
            3
        }
        ToggleBtnState.LAST_WEEK -> {
            historyViewModel.checkedLastWeekButton()
            2
        }
    }

    LaunchedEffect(navigateToDetailState) {
        navigateToDetailState?.let { state ->
            if(state.navigateToDetail){
                onNavigateToDetail(state.dayId!!)
            }
            historyViewModel.doneNavigateToDetail()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        uiDialog?.let {
            Dialog(dialog = it)
        }

        ToggleBtnGroup(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
            currentToggleBtnState = toggleBtnState,
            onClick = { historyViewModel.onClickCheckedItem(it) })

        HistoryHeaderText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.padding_normal_0)),
            dayList = listDays,
            myAnalystForHistory = myAnalystForHistory
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(cellsAmount),
            modifier = Modifier.fillMaxSize()
        ) {
            items(listDays) { day ->
                DaysListItem(
                    day = day,
                    onClick = { historyViewModel.onClickDay(it) },
                    onLongClick = { historyViewModel.onClickLongDay(it) },
                )

            }
        }
    }
}
