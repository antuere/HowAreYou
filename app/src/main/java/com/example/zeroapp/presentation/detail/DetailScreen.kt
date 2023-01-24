package com.example.zeroapp.presentation.detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import com.example.zeroapp.presentation.detail.state.DetailSideEffect
import com.example.zeroapp.presentation.detail.ui_compose.DayDetailView
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun DetailScreen(
    updateAppBar: (AppBarState) -> Unit,
    onNavigateUp: () -> Unit,
    showDialog: (UIDialog) -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    Timber.i("MVI error test : enter in detail screen")

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleDeleteBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)
    val rotation = remember { Animatable(initialValue = 0F) }

    val viewState by detailViewModel.collectAsState()

    LaunchedEffect(viewState.isFavorite) {
        updateAppBar(
            AppBarState(
                titleId = R.string.about_day,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                actions = {
                    IconButton(onClick = detailViewModel::onClickFavoriteButton) {
                        Icon(
                            modifier = Modifier.graphicsLayer {
                                rotationY = rotation.value
                            },
                            painter = painterResource(id = viewState.favoriteBtnRes),

                            //  TODO добавить описание всех картинок в проект
                            contentDescription = null
                        )
                    }
                    IconButton(
                        modifier = Modifier.graphicsLayer {
                            scaleX = scaleDeleteBtn
                            scaleY = scaleDeleteBtn
                        },
                        onClick = detailViewModel::onClickDeleteButton,
                        interactionSource = interactionSource
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = null
                        )
                    }
                },
                isVisibleBottomBar = false
            )
        )
    }

    detailViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            DetailSideEffect.AnimateFavoriteBtn -> {
                rotation.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(durationMillis = 450),
                )
                rotation.snapTo(0F)
            }
            is DetailSideEffect.Dialog -> {
                showDialog(sideEffect.uiDialog)
            }
            DetailSideEffect.NavigateUp -> {
                onNavigateUp()
            }
        }
    }

    if (viewState.isLoading) {
        FullScreenProgressIndicator()
    } else {
        DayDetailView(
            imageResId = viewState.daySmileRes,
            dateString = viewState.dateString,
            dayText = viewState.dayText
        )
    }
}