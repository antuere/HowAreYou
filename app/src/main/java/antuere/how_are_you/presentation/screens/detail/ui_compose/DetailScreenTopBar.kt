package antuere.how_are_you.presentation.screens.detail.ui_compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.IconButtonScaleable
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.TopBarType
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.detail.state.DetailIntent

@Composable
fun DetailScreenTopBar(
    favoriteBtnRes: Int,
    rotation: () -> Float,
    onIntent: (DetailIntent) -> Unit,
    isEditMode: Boolean,
    dateString: String,
) {
    val appState = LocalAppState.current

    LaunchedEffect(favoriteBtnRes, isEditMode, dateString) {
        when (isEditMode) {
            true -> {
                appState.updateAppBar(
                    AppBarState(
                        topBarTitle = UiText.StringResource(R.string.day_edit),
                        topBarType = TopBarType.CENTER_ALIGNED,
                        navigationIcon = Icons.Rounded.Close,
                        onClickNavigationBtn = { onIntent(DetailIntent.EditModeOff) },
                        actions = {
                            IconButtonScaleable(
                                onClick = { onIntent(DetailIntent.SaveBtnClicked) },
                                iconRes = R.drawable.ic_check
                            )
                        },
                        isVisibleBottomBar = false
                    )
                )
            }

            false -> {
                appState.updateAppBar(
                    AppBarState(
                        topBarTitle = UiText.String(dateString),
                        topBarType = TopBarType.SMALL,
                        navigationIcon = Icons.Filled.ArrowBack,
                        onClickNavigationBtn = appState::navigateUp,
                        actions = {
                            IconButton(onClick = { onIntent(DetailIntent.FavoriteBtnClicked) }) {
                                Icon(
                                    modifier = Modifier.graphicsLayer { rotationY = rotation() },
                                    painter = painterResource(id = favoriteBtnRes),
                                    contentDescription = null
                                )
                            }
                            IconButtonScaleable(
                                onClick = { onIntent(DetailIntent.EditModeOn) },
                                iconRes = R.drawable.ic_edit
                            )
                            IconButtonScaleable(
                                onClick = { onIntent(DetailIntent.DeleteBtnClicked) },
                                iconRes = R.drawable.ic_delete
                            )
                        },
                        isVisibleBottomBar = false
                    )
                )
            }
        }
    }
}
