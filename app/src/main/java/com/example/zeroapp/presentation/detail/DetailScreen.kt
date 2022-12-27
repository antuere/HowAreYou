package com.example.zeroapp.presentation.detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    onComposing: (AppBarState, Boolean) -> Unit,
    onNavigateUp: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()

    val uiDialog by detailViewModel.uiDialog.collectAsState()
    val selectedDay by detailViewModel.selectedDay.collectAsState()
    val navigateToHistory by detailViewModel.navigateToHistory.collectAsState()
    val rotation = remember { Animatable(initialValue = 360f) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleDeleteBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)


    LaunchedEffect(navigateToHistory) {
        if (navigateToHistory) {
            onNavigateUp()
            detailViewModel.navigateDone()
        }
    }

    uiDialog?.let {
        Dialog(dialog = it)
    }

    selectedDay?.let { day ->
        var isFavoriteDay by remember {
            mutableStateOf(day.isFavorite)
        }

        LaunchedEffect(isFavoriteDay) {
            onComposing(
                AppBarState(
                    titleId = R.string.about_day,
                    navigationIcon = Icons.Filled.ArrowBack,
                    navigationOnClick = { onNavigateUp() },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                rotation.animateTo(
                                    targetValue = if (isFavoriteDay) 0f else 360f,
                                    animationSpec = tween(durationMillis = 450),
                                )
                            }
                            detailViewModel.onClickFavoriteButton()
                            isFavoriteDay = isFavoriteDay.not()
                        }) {
                            Icon(
                                modifier = Modifier.graphicsLayer {
                                    rotationY = rotation.value
                                },
                                painter = if (isFavoriteDay) painterResource(id = R.drawable.ic_baseline_favorite)
                                else painterResource(id = R.drawable.ic_baseline_favorite_border),

                                //  TODO добавить описание всех картинок в проект
                                contentDescription = null
                            )
                        }
                        IconButton(
                            modifier = Modifier.graphicsLayer {
                                scaleX = scaleDeleteBtn
                                scaleY = scaleDeleteBtn
                            },
                            onClick = { detailViewModel.onClickDeleteButton() },
                            interactionSource = interactionSource
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null
                            )
                        }
                    }
                ),
                false
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.05F))

            Image(
                modifier = Modifier
                    .fillMaxSize(0.5F)
                    .weight(0.2F),
                painter = painterResource(id = day.imageResId),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

            Text(
                text = day.dateString,
                fontSize = dimensionResource(id = R.dimen.textSize_big_1).value.sp
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

            Text(
                modifier = Modifier
                    .weight(0.7F)
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2)),
                text = day.dayText,
                fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp
            )
        }
    }
}