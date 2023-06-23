package antuere.how_are_you.presentation.screens.history.ui_compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.OutlinedButtonWithIcon
import antuere.how_are_you.presentation.base.ui_compose_components.days_grid.DaysGrid
import antuere.how_are_you.presentation.base.ui_compose_components.days_grid.DaysGridShimmer
import antuere.how_are_you.presentation.screens.history.state.HistoryIntent
import antuere.how_are_you.presentation.screens.history.state.HistoryState
import antuere.how_are_you.presentation.screens.history.ui_compose.components.CalendarImage
import antuere.how_are_you.presentation.screens.history.ui_compose.components.DaysNotFoundPlug
import antuere.how_are_you.presentation.screens.history.ui_compose.components.HistoryHeaderText
import antuere.how_are_you.presentation.screens.history.ui_compose.components.HistoryScreenTopBar
import antuere.how_are_you.presentation.screens.history.ui_compose.components.HistoryScreenTopBarWithAction
import antuere.how_are_you.presentation.screens.history.ui_compose.components.SmileBoxPlug
import antuere.how_are_you.presentation.screens.history.ui_compose.components.ToggleBtnGroup
import antuere.how_are_you.presentation.screens.history.ui_compose.components.date_picker.DefaultDateRangePicker
import antuere.how_are_you.util.extensions.paddingBotAndTopBar


@Composable
fun HistoryScreenContent(
    viewState: () -> HistoryState,
    lazyGridState: () -> LazyGridState,
    onIntent: (HistoryIntent) -> Unit,
    rotation: () -> Float,
    isShowShadow: () -> Boolean,
) {
    var isShowDatePicker by remember { mutableStateOf(false) }

    if (isShowDatePicker) {
        DefaultDateRangePicker(
            onDismissRequest = { isShowDatePicker = false },
            dateSelected = { onIntent(HistoryIntent.DaysInFilterSelected(it)) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingBotAndTopBar(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = viewState()) {
            is HistoryState.Empty.FromFilter -> {
                HistoryScreenTopBarWithAction(filterBtnClicked = {
                    isShowDatePicker = true
                })

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

                DaysNotFoundPlug(message = state.message)
                Spacer(modifier = Modifier.weight(1F))
            }

            is HistoryState.Empty.FromToggleGroup -> {
                HistoryScreenTopBarWithAction(filterBtnClicked = {
                    isShowDatePicker = true
                })

                ToggleBtnGroup(
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                    currentToggleBtnState = state.toggleBtnState,
                    toggleButtons = state.toggleButtons,
                    onClick = { onIntent(HistoryIntent.ToggleBtnChanged(it)) },
                )

                Spacer(modifier = Modifier.weight(1F))

                CalendarImage(
                    calendarImageRes = state.calendarImageRes,
                    toggleBtnState = state.toggleBtnState,
                    description = state.message
                )

                Spacer(modifier = Modifier.weight(1F))
            }

            is HistoryState.Empty.NoEntriesYet -> {
                HistoryScreenTopBar()
                SmileBoxPlug(message = state.message)
            }

            is HistoryState.Loaded.Default -> {
                HistoryScreenTopBarWithAction(filterBtnClicked = {
                    isShowDatePicker = true
                })

                ToggleBtnGroup(
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                    currentToggleBtnState = state.toggleBtnState,
                    toggleButtons = state.toggleButtons,
                    onClick = { onIntent(HistoryIntent.ToggleBtnChanged(it)) },
                )

                HistoryHeaderText(
                    rotation = rotation,
                    headerText = state.textHeadline.asString(),
                    isShowShadow = isShowShadow
                )

                DaysGrid(
                    cellsAmount = state.cellsAmountForGrid,
                    days = state.dayList,
                    lazyGridState = lazyGridState(),
                    onClick = { onIntent(HistoryIntent.DayClicked(it)) },
                    onLongClick = { onIntent(HistoryIntent.DayLongClicked(it)) }
                )
            }

            is HistoryState.Loaded.FilterSelected -> {
                HistoryScreenTopBarWithAction(filterBtnClicked = {
                    isShowDatePicker = true
                })

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
                    headerText = state.textHeadline.asString(),
                    isShowShadow = isShowShadow
                )

                DaysGrid(
                    cellsAmount = state.cellsAmountForGrid,
                    days = state.dayList,
                    lazyGridState = lazyGridState(),
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
                    cellsAmount = state.cellsAmount, aspectRatio = state.aspectRatioForItem
                )
            }
        }
    }
}

