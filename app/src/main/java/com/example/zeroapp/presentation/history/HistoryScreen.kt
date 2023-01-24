package com.example.zeroapp.presentation.history

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.buttons.OutlinedButtonWithIcon
import com.example.zeroapp.presentation.base.ui_compose_components.days_list.DaysGrid
import com.example.zeroapp.presentation.base.ui_compose_components.days_list.DaysGridShimmer
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.history.state.HistorySideEffect
import com.example.zeroapp.presentation.history.state.HistoryState
import com.example.zeroapp.presentation.history.ui_compose.*
import com.example.zeroapp.util.paddingForBotAndTopBar
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    onNavigateToDetail: (Long) -> Unit,
    updateAppBar: (AppBarState) -> Unit,
    dismissSnackbar: () -> Unit,
    showDialog: (UIDialog) -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in history screen")

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(initialValue = 360f) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleFilterBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)

    val viewState by historyViewModel.collectAsState()

    val isEnabledHandler = remember {
        bottomSheetState.currentValue == ModalBottomSheetValue.Expanded
    }

    val hideBottomSheet: () -> Unit = remember {
        {
            scope.launch {
                bottomSheetState.hide()
            }
        }
    }

    val showBottomSheet: () -> Unit = remember {
        {
            scope.launch {
                bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
            }
        }
    }

    val onDaysSelected: (LocalDate, LocalDate) -> Unit = remember {
        { startDate, endDate ->
            historyViewModel.onDaysSelected(startDate, endDate)
        }
    }

    val onDayClick : (Day) -> Unit = remember {
        { historyViewModel.onClickDay(it) }
    }

    val onDayClickLong : (Day) -> Unit = remember {
        { historyViewModel.onClickLongDay(it) }
    }

    BackHandler(enabled = isEnabledHandler) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(true) {
        dismissSnackbar()
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        updateAppBar(
            AppBarState(
                titleId = R.string.history,
                actions = {
                    IconButton(
                        modifier = Modifier.graphicsLayer {
                            scaleY = scaleFilterBtn
                            scaleX = scaleFilterBtn
                        },
                        onClick = showBottomSheet,
                        interactionSource = interactionSource
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = null
                        )
                    }
                },
                isVisibleBottomBar = bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
            ),
        )
    }

    historyViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HistorySideEffect.AnimationHistoryHeader -> {
                rotation.animateTo(
                    targetValue = if (rotation.value == 360F) 0f else 360f,
                    animationSpec = tween(durationMillis = 300),
                )
            }
            is HistorySideEffect.Dialog -> showDialog(sideEffect.uiDialog)
            is HistorySideEffect.NavigationToDayDetail -> onNavigateToDetail(sideEffect.dayId)
        }
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
                    hideBottomSheet = hideBottomSheet,
                    onDaysSelected = onDaysSelected
                )
            }
        }) {
        Column(
            modifier = Modifier.fillMaxSize().paddingForBotAndTopBar(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewState) {
                is HistoryState.Empty.FromFilter -> {
                    OutlinedButtonWithIcon(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        onClick = {
                            historyViewModel.onClickCheckedItem(ToggleBtnState.CURRENT_MONTH)
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
                        onClick = { historyViewModel.onClickCheckedItem(it) },
                    )

                    Spacer(modifier = Modifier.weight(1F))

                    Text(state.message.asString())
                    Spacer(modifier = Modifier.weight(1F))
                }
                is HistoryState.Empty.NoEntriesYet -> {
                    LaunchedEffect(true) {
                        updateAppBar(
                            AppBarState(
                                titleId = R.string.history,
                                isVisibleBottomBar = true
                            ),
                        )
                    }

                    Text(state.message.asString())
                }
                is HistoryState.Loaded.Default -> {

                    ToggleBtnGroup(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        currentToggleBtnState = state.toggleBtnState,
                        toggleButtons = state.toggleButtons,
                        onClick = { historyViewModel.onClickCheckedItem(it) },
                    )

                    HistoryHeaderText(
                        rotation = { rotation.value },
                        headerText = state.textHeadline
                    )

                    DaysGrid(
                        cellsAmount = state.cellsAmountForGrid,
                        days = state.dayList,
                        onClick = onDayClick,
                        onLongClick = onDayClickLong
                    )

                }
                is HistoryState.Loaded.FilterSelected -> {

                    OutlinedButtonWithIcon(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        onClick = {
                            historyViewModel.onClickCheckedItem(ToggleBtnState.CURRENT_MONTH)
                        },
                        isIconInStart = false,
                        labelId = R.string.close_filter,
                        iconId = R.drawable.ic_round_close
                    )

                    HistoryHeaderText(
                        rotation = { rotation.value },
                        headerText = state.textHeadline
                    )

                    DaysGrid(
                        cellsAmount = state.cellsAmountForGrid,
                        days = state.dayList,
                        onClick = onDayClick,
                        onLongClick = onDayClickLong
                    )
                }

                is HistoryState.LoadingShimmer -> {

                    ToggleBtnGroup(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        currentToggleBtnState = state.toggleBtnState,
                        toggleButtons = state.toggleButtons,
                        onClick = { historyViewModel.onClickCheckedItem(it) },
                    )

                    HistoryHeaderText(headerText = state.dateTextPlug)

                    DaysGridShimmer(
                        cellsAmount = state.cellsAmount,
                        aspectRatio = state.aspectRatioForItem
                    )
                }
            }
        }
    }
}

