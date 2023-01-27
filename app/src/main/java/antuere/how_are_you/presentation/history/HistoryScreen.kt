package antuere.how_are_you.presentation.history

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
import antuere.domain.dto.ToggleBtnState
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.OutlinedButtonWithIcon
import antuere.how_are_you.presentation.base.ui_compose_components.days_list.DaysGrid
import antuere.how_are_you.presentation.base.ui_compose_components.days_list.DaysGridShimmer
import antuere.how_are_you.presentation.history.state.HistoryIntent
import antuere.how_are_you.presentation.history.state.HistorySideEffect
import antuere.how_are_you.presentation.history.state.HistoryState
import antuere.how_are_you.presentation.history.ui_compose.*
import antuere.how_are_you.util.paddingBotAndTopBar
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    onNavigateToDetail: (Long) -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    Timber.i("MVI error test : enter in history screen")
    val appState = LocalAppState.current
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(initialValue = 360f) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleFilterBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)

    val viewState by viewModel.collectAsState()

    val isEnabledHandler = remember(bottomSheetState.currentValue) {
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
            HistoryIntent.DaysInFilterSelected(
                startDate = startDate,
                endDate = endDate
            ).run(viewModel::onIntent)
        }
    }

    BackHandler(enabled = isEnabledHandler) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(true) {
      appState.dismissSnackbar()
    }

    LaunchedEffect(bottomSheetState.targetValue) {
       appState.updateAppBar(
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

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HistorySideEffect.AnimationHistoryHeader -> {
                rotation.animateTo(
                    targetValue = if (rotation.value == 360F) 0f else 360f,
                    animationSpec = tween(durationMillis = 300)
                )
            }
            is HistorySideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
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
            modifier = Modifier
                .fillMaxSize()
                .paddingBotAndTopBar(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewState) {
                is HistoryState.Empty.FromFilter -> {
                    OutlinedButtonWithIcon(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        onClick = {
                            HistoryIntent.ToggleBtnChanged(ToggleBtnState.CURRENT_MONTH)
                                .run(viewModel::onIntent)
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
                        onClick = {
                            HistoryIntent.ToggleBtnChanged(it).run(viewModel::onIntent)
                        },
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
                        onClick = {
                            HistoryIntent.ToggleBtnChanged(it).run(viewModel::onIntent)
                        }
                    )

                    HistoryHeaderText(
                        rotation = { rotation.value },
                        headerText = state.textHeadline.asString()
                    )

                    DaysGrid(
                        cellsAmount = state.cellsAmountForGrid,
                        days = state.dayList,
                        onClick = { HistoryIntent.DayClicked(it).run(viewModel::onIntent) },
                        onLongClick = { HistoryIntent.DayLongClicked(it).run(viewModel::onIntent) }
                    )

                }
                is HistoryState.Loaded.FilterSelected -> {
                    OutlinedButtonWithIcon(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        onClick = {
                            HistoryIntent.ToggleBtnChanged(ToggleBtnState.CURRENT_MONTH)
                                .run(viewModel::onIntent)
                        },
                        isIconInStart = false,
                        labelId = R.string.close_filter,
                        iconId = R.drawable.ic_round_close
                    )

                    HistoryHeaderText(
                        rotation = { rotation.value },
                        headerText = state.textHeadline.asString()
                    )

                    DaysGrid(
                        cellsAmount = state.cellsAmountForGrid,
                        days = state.dayList,
                        onClick = { HistoryIntent.DayClicked(it).run(viewModel::onIntent) },
                        onLongClick = { HistoryIntent.DayLongClicked(it).run(viewModel::onIntent) }
                    )
                }

                is HistoryState.LoadingShimmer -> {
                    ToggleBtnGroup(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                        currentToggleBtnState = state.toggleBtnState,
                        toggleButtons = state.toggleButtons,
                        onClick = {
                            HistoryIntent.ToggleBtnChanged(it).run(viewModel::onIntent)
                        }
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

