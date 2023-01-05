package com.example.zeroapp.presentation.history

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.domain.dto.ToggleBtnState
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.base.ui_compose_components.DaysListItem
import com.example.zeroapp.presentation.base.ui_compose_components.buttons.OutlinedButtonWithIcon
import com.example.zeroapp.presentation.history.ui_compose.DaysFilterBottomSheet
import com.example.zeroapp.presentation.history.ui_compose.HistoryHeaderText
import com.example.zeroapp.presentation.history.ui_compose.ToggleBtnGroup
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
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

    val rotation = remember { Animatable(initialValue = 360f) }

    val uiDialog by historyViewModel.uiDialog.collectAsState()
    val listDays by historyViewModel.listDays.collectAsState()
    val toggleBtnState by historyViewModel.toggleBtnState.collectAsState()
    val cellsAmount by historyViewModel.cellsAmount.collectAsState()
    val navigateToDetailState by historyViewModel.navigateToDetailState.collectAsState()
    val isShowAnimation by historyViewModel.isShowAnimation.collectAsState()

    var isShowToggleBtnGroup by remember {
        mutableStateOf(true)
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleFilterBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)

    uiDialog?.let {
        Dialog(dialog = it)
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        onComposing(
            AppBarState(
                titleId = R.string.history,
                actions = {
                    if (isShowToggleBtnGroup) {
                        IconButton(
                            modifier = Modifier.graphicsLayer {
                                scaleY = scaleFilterBtn
                                scaleX = scaleFilterBtn
                            },
                            onClick = {
                                scope.launch {
                                    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            },
                            interactionSource = interactionSource
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.FilterList,
                                contentDescription = null
                            )
                        }
                    }
                }
            ),
            bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
        )
    }

    LaunchedEffect(navigateToDetailState) {
        navigateToDetailState?.let { state ->
            if (state.navigateToDetail) {
                onNavigateToDetail(state.dayId!!)
            }
            historyViewModel.doneNavigateToDetail()
        }
    }

    LaunchedEffect(toggleBtnState) {
        if (toggleBtnState == ToggleBtnState.FILTER_SELECTED) {
            isShowToggleBtnGroup = false
        }

        if (isShowAnimation) {
            rotation.animateTo(
                targetValue = if (rotation.value == 360F) 0f else 360f,
                animationSpec = tween(durationMillis = 300),
            )
            historyViewModel.resetIsShowAnimation()
        }
    }

    listDays?.let { days ->
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (toggleBtnState == ToggleBtnState.FILTER_SELECTED) {
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
                    OutlinedButtonWithIcon(
                        onClick = {
                            isShowToggleBtnGroup = true
                            historyViewModel.onClickCheckedItem(ToggleBtnState.CURRENT_MONTH)
                        },
                        isIconInStart = false,
                        labelId = R.string.close_filter,
                        iconId = R.drawable.ic_round_close
                    )
                }

                if (isShowToggleBtnGroup) {
                    ToggleBtnGroup(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        currentToggleBtnState = toggleBtnState,
                        onClick = { historyViewModel.onClickCheckedItem(it) },
                    )
                }

                if (days.isEmpty()) {
                    when (toggleBtnState) {
                        ToggleBtnState.ALL_DAYS -> {
                            isShowToggleBtnGroup = false
                            Text(text = stringResource(R.string.no_days_all))
                        }
                        ToggleBtnState.CURRENT_MONTH -> {
                            isShowToggleBtnGroup = true
                            Spacer(modifier = Modifier.weight(1F))
                            Text(text = stringResource(R.string.no_days_month))
                            Spacer(modifier = Modifier.weight(1F))

                        }
                        ToggleBtnState.LAST_WEEK -> {
                            isShowToggleBtnGroup = true
                            Spacer(modifier = Modifier.weight(1F))
                            Text(text = stringResource(R.string.no_days_week))
                            Spacer(modifier = Modifier.weight(1F))
                        }
                        ToggleBtnState.FILTER_SELECTED -> {
                            Spacer(modifier = Modifier.weight(1F))
                            Text(text = stringResource(R.string.no_days_filter))
                            Spacer(modifier = Modifier.weight(1F))
                        }
                    }
                } else {
                    HistoryHeaderText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(id = R.dimen.padding_normal_0))
                            .graphicsLayer {
                                rotationX = rotation.value
                            },
                        dayList = days,
                        myAnalystForHistory = myAnalystForHistory
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(cellsAmount),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = days,
                            key = { it.dayId }
                        ) { day ->
                            DaysListItem(
                                modifier = Modifier
                                    .animateItemPlacement(
                                        animationSpec = tween(
                                            durationMillis = 200
                                        )
                                    ),
                                day = day,
                                onClick = { historyViewModel.onClickDay(it) },
                                onLongClick = { historyViewModel.onClickLongDay(it) },
                            )
                        }
                    }
                }
            }
        }
    }
}
