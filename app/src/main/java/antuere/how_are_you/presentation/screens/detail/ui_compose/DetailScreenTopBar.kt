package antuere.how_are_you.presentation.screens.detail.ui_compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.detail.state.DetailIntent
import timber.log.Timber

@Composable
fun DetailScreenTopBar(
    favoriteBtnRes: () -> Int,
    rotation: () -> Float,
    onIntent: (DetailIntent) -> Unit,
) {
    Timber.i("MVI error test : enter in topbar ")

    val appState = LocalAppState.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleDeleteBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)

    LaunchedEffect(favoriteBtnRes()) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.about_day,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                actions = {
                    IconButton(onClick = { onIntent(DetailIntent.FavoriteBtnClicked) }) {
                        Icon(
                            modifier = Modifier.graphicsLayer { rotationY = rotation() },
                            painter = painterResource(id = favoriteBtnRes()),
                            //  TODO добавить описание всех картинок в проект
                            contentDescription = null
                        )
                    }
                    IconButton(
                        modifier = Modifier.graphicsLayer {
                            scaleX = scaleDeleteBtn
                            scaleY = scaleDeleteBtn
                        },
                        onClick = { onIntent(DetailIntent.DeleteBtnClicked) },
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
}
