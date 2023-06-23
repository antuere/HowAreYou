package antuere.how_are_you.presentation.screens.favorites.ui_compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import antuere.how_are_you.presentation.base.ui_compose_components.days_grid.DaysGrid
import antuere.how_are_you.presentation.base.ui_compose_components.days_grid.DaysGridShimmer
import antuere.how_are_you.presentation.screens.favorites.state.FavoritesIntent
import antuere.how_are_you.presentation.screens.favorites.state.FavoritesState
import antuere.how_are_you.presentation.screens.favorites.ui_compose.components.SmileFavPlug
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun FavoritesScreenContent(
    viewState: () -> FavoritesState,
    onIntent: (FavoritesIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = viewState()) {
            is FavoritesState.Empty -> {
                SmileFavPlug(message = state.message)
            }
            is FavoritesState.Loaded -> {
                Spacer(modifier = Modifier.height(10.dp))
                DaysGrid(
                    cellsAmount = state.cellsAmountForGrid,
                    days = state.dayList,
                    onClick = { onIntent(FavoritesIntent.DayClicked(it)) },
                    onLongClick = { onIntent(FavoritesIntent.DayLongClicked(it)) })
            }
            is FavoritesState.LoadingShimmer -> {
                DaysGridShimmer(
                    cellsAmount = state.cellsAmount,
                    aspectRatio = state.aspectRatioForItem
                )
            }
        }
    }
}