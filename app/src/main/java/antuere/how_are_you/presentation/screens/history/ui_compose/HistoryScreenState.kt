package antuere.how_are_you.presentation.screens.history.ui_compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.OutlinedButtonWithIcon
import antuere.how_are_you.presentation.base.ui_compose_components.days_list.DaysGrid
import antuere.how_are_you.presentation.base.ui_compose_components.days_list.DaysGridShimmer
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.history.state.HistoryIntent
import antuere.how_are_you.presentation.screens.history.state.HistoryState
import antuere.how_are_you.presentation.screens.history.ui_compose.components.DaysFilterBottomSheet
import antuere.how_are_you.presentation.screens.history.ui_compose.components.HistoryHeaderText
import antuere.how_are_you.presentation.screens.history.ui_compose.components.ToggleBtnGroup
import antuere.how_are_you.util.extensions.findFragmentActivity
import antuere.how_are_you.util.extensions.paddingBotAndTopBar
import timber.log.Timber
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreenState(
    viewState: () -> HistoryState,
    onIntent: (HistoryIntent) -> Unit,
    bottomSheetState: ModalBottomSheetState,
    rotation: () -> Float,
) {
    Timber.i("MVI error test : historyScreenState")
    val activity = LocalContext.current.findFragmentActivity()
    val appState = LocalAppState.current
    val isEnabledHandler = remember(bottomSheetState.currentValue) {
        bottomSheetState.currentValue == ModalBottomSheetValue.Expanded
    }

    val isSheetStartsHiding by remember {
        derivedStateOf {
            bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
                    && bottomSheetState.isAnimationRunning
        }
    }

    LaunchedEffect(isSheetStartsHiding) {
        if (isSheetStartsHiding) {
            onIntent(HistoryIntent.FilterSheetClosed)
        }
    }

    BackHandler(enabled = isEnabledHandler) {
        onIntent(HistoryIntent.FilterSheetClosed)
    }

    BackHandler(enabled = !isEnabledHandler) {
        activity.finish()
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.onPrimary,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                DaysFilterBottomSheet(
                    onDaysSelected = { startDate: LocalDate, endDate: LocalDate ->
                        onIntent(
                            HistoryIntent.DaysInFilterSelected(
                                startDate = startDate,
                                endDate = endDate
                            )
                        )
                    }
                )
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingBotAndTopBar(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewState()) {
                is HistoryState.Empty.FromFilter -> {
                    OutlinedButtonWithIcon(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        onClick = {
                            onIntent(HistoryIntent.FilterCloseBtnClicked)
                        },
                        isIconInStart = false,
                        labelId = R.string.close_filter,
                        iconId = R.drawable.ic_round_close
                    )
                    Spacer(modifier = Modifier.weight(1F))

                    Text(state.message.asString())
                    Spacer(modifier = Modifier.weight(1F))
                }
                is HistoryState.Empty.FromToggleGroup -> {
                    ToggleBtnGroup(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        currentToggleBtnState = state.toggleBtnState,
                        toggleButtons = state.toggleButtons,
                        onClick = { onIntent(HistoryIntent.ToggleBtnChanged(it)) },
                    )

                    Spacer(modifier = Modifier.weight(1F))

                    Text(state.message.asString())
                    Spacer(modifier = Modifier.weight(1F))
                }
                is HistoryState.Empty.NoEntriesYet -> {
                    LaunchedEffect(true) {
                        appState.updateAppBar(
                            AppBarState(
                                titleId = R.string.history,
                                isVisibleBottomBar = true
                            )
                        )
                    }

                    Text(state.message.asString())
                }
                is HistoryState.Loaded.Default -> {
                    ToggleBtnGroup(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        currentToggleBtnState = state.toggleBtnState,
                        toggleButtons = state.toggleButtons,
                        onClick = { onIntent(HistoryIntent.ToggleBtnChanged(it)) },
                    )

                    HistoryHeaderText(
                        rotation = rotation,
                        headerText = state.textHeadline.asString()
                    )

                    DaysGrid(
                        cellsAmount = state.cellsAmountForGrid,
                        days = state.dayList,
                        onClick = { onIntent(HistoryIntent.DayClicked(it)) },
                        onLongClick = { onIntent(HistoryIntent.DayLongClicked(it)) }
                    )

                }
                is HistoryState.Loaded.FilterSelected -> {
                    OutlinedButtonWithIcon(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        onClick = {
                            onIntent(HistoryIntent.FilterCloseBtnClicked)
                        },
                        isIconInStart = false,
                        labelId = R.string.close_filter,
                        iconId = R.drawable.ic_round_close
                    )

                    HistoryHeaderText(
                        rotation = rotation,
                        headerText = state.textHeadline.asString()
                    )

                    DaysGrid(
                        cellsAmount = state.cellsAmountForGrid,
                        days = state.dayList,
                        onClick = { onIntent(HistoryIntent.DayClicked(it)) },
                        onLongClick = { onIntent(HistoryIntent.DayLongClicked(it)) }
                    )
                }

                is HistoryState.LoadingShimmer -> {
                    ToggleBtnGroup(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        currentToggleBtnState = state.toggleBtnState,
                        toggleButtons = state.toggleButtons,
                        onClick = { onIntent(HistoryIntent.ToggleBtnChanged(it)) },
                    )

                    HistoryHeaderText(headerText = state.dateTextPlug.asString())

                    DaysGridShimmer(
                        cellsAmount = state.cellsAmount,
                        aspectRatio = state.aspectRatioForItem
                    )
                }
            }
        }
    }
}