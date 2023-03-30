package antuere.how_are_you.presentation.screens.history.ui_compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState

@Composable
fun HistoryScreenTopBarWithAction(
    filterBtnClicked: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleFilterBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)
    val appState = LocalAppState.current

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.history,
                actions = {
                    IconButton(
                        modifier = Modifier.graphicsLayer {
                            scaleY = scaleFilterBtn
                            scaleX = scaleFilterBtn
                        },
                        onClick = filterBtnClicked,
                        interactionSource = interactionSource
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = null
                        )
                    }
                },
                isVisibleBottomBar = true
            ),
        )
    }
}