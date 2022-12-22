package com.example.zeroapp.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.domain.dto.ToggleBtnState
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.base.ui_compose_components.DaysListItem
import com.example.zeroapp.presentation.history.ui_compose.DaysFilterBottomSheet
import com.example.zeroapp.presentation.history.ui_compose.HistoryHeaderText
import com.example.zeroapp.presentation.history.ui_compose.ToggleBtnGroup
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    onNavigateToDetail: (Long) -> Unit,
    onComposing: (AppBarState, Boolean) -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel(),
    myAnalystForHistory: MyAnalystForHistory
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(bottomSheetState.targetValue) {
        onComposing(
            AppBarState(
                titleId = R.string.history,
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = null
                        )
                    }
                }
            ),
            bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
        )
    }

    val uiDialog by historyViewModel.uiDialog.collectAsState()
    val listDays by historyViewModel.listDays.collectAsState()
    val isFilterSelected by historyViewModel.isDaysSelected.collectAsState()
    val toggleBtnState by historyViewModel.toggleBtnState.collectAsState()
    val navigateToDetailState by historyViewModel.navigateToDetailState.collectAsState()


    var cellsAmount = when (toggleBtnState) {
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
        ToggleBtnState.NONE -> {
            3
        }

    }

    LaunchedEffect(navigateToDetailState) {
        navigateToDetailState?.let { state ->
            if (state.navigateToDetail) {
                onNavigateToDetail(state.dayId!!)
            }
            historyViewModel.doneNavigateToDetail()
        }
    }

    uiDialog?.let {
        Dialog(dialog = it)
    }

    if (isFilterSelected) {
        cellsAmount = 3
        historyViewModel.resetIsFilterSelected()
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                DaysFilterBottomSheet(
                    bottomSheetState = bottomSheetState,
                    onDaysSelected = { historyViewModel.onDaysSelected(it) })
            }
        }) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            ToggleBtnGroup(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                currentToggleBtnState = toggleBtnState,
                onClick = { historyViewModel.onClickCheckedItem(it) },
            )

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
}
