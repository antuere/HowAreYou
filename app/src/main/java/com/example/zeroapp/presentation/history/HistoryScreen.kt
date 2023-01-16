package com.example.zeroapp.presentation.history

import androidx.compose.animation.Crossfade
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.domain.dto.ToggleBtnState
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.DaysListItem
import com.example.zeroapp.presentation.base.ui_compose_components.buttons.OutlinedButtonWithIcon
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import com.example.zeroapp.presentation.history.state.HistorySideEffect
import com.example.zeroapp.presentation.history.state.HistoryState
import com.example.zeroapp.presentation.history.ui_compose.DaysFilterBottomSheet
import com.example.zeroapp.presentation.history.ui_compose.HistoryHeaderText
import com.example.zeroapp.presentation.history.ui_compose.HistoryHeaderTextPlug
import com.example.zeroapp.presentation.history.ui_compose.ToggleBtnGroup
import com.example.zeroapp.util.shimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    onNavigateToDetail: (Long) -> Unit,
    updateAppBar: (AppBarState) -> Unit,
    dismissSnackbar: () -> Unit,
    showDialog: (UIDialog) -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(initialValue = 360f) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleFilterBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)

    val viewState by historyViewModel.collectAsState()

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
            is HistorySideEffect.Dialog -> {
                showDialog(sideEffect.uiDialog)
            }
            is HistorySideEffect.NavigationToDayDetail -> {
                onNavigateToDetail(sideEffect.dayId)
            }
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
                    bottomSheetState = bottomSheetState,
                    onDaysSelected = { historyViewModel.onDaysSelected(it) })
            }
        }) {

        Crossfade(targetState = viewState, animationSpec = tween(300)) { state ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (state) {
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
                            onClick = { historyViewModel.onClickCheckedItem(it) },
                        )

                        HistoryHeaderText(
                            modifier = Modifier
                                .graphicsLayer {
                                    rotationX = rotation.value
                                },
                            dayList = state.days
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(state.cellsAmount),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = state.days,
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
                            modifier = Modifier
                                .graphicsLayer {
                                    rotationX = rotation.value
                                },
                            dayList = state.days
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(state.cellsAmount),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = state.days,
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

                    is HistoryState.LoadingShimmer -> {
                        ToggleBtnGroup(
                            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_2)),
                            currentToggleBtnState = state.toggleBtnState,
                            onClick = { historyViewModel.onClickCheckedItem(it) },
                        )

                        HistoryHeaderTextPlug(
                            modifier = Modifier
                                .graphicsLayer {
                                    rotationX = rotation.value
                                },
                            plugText = state.dateTextPlug
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(state.cellsAmount),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(10) {
                                Card(
                                    modifier = Modifier
                                        .padding(dimensionResource(id = R.dimen.padding_small_0))
                                        .shimmer(500),
                                    shape = MaterialTheme.shapes.large,
                                    colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.7F))
                                ) {
                                    Box(
                                        modifier = Modifier.aspectRatio(state.aspectRatioForItem)
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
