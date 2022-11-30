package com.example.zeroapp.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.history.ui_compose.DaysListItem
import com.example.zeroapp.presentation.history.ui_compose.HistoryHeaderText
import com.example.zeroapp.presentation.history.ui_compose.ToggleBtnGroup

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
            dayList = getMockDays(),
            myAnalystForHistory = myAnalystForHistory
        )

        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            items(getMockDays()) { day ->
                DaysListItem(
                    day = day,
                    onClick = { historyViewModel.onClickDay(it) },
                    onLongClick = { historyViewModel.onClickLongDay(it) },
                )

            }
        }

    }
}


private fun getMockDays(): List<Day> {
    val result = mutableListOf<Day>()
    for (i in 1..10) {
        val testDay = Day(
            dayId = i.toLong(),
            imageResId = antuere.data.R.drawable.smile_happy,
            dayText = "Nice day $i",
            dateString = "$i.11.2022",
            transitionName = "noup",
            isFavorite = false
        )
        result.add(testDay)
    }
    return result
}