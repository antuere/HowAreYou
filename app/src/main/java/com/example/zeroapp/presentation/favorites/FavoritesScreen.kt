package com.example.zeroapp.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.days_list.DaysGrid
import com.example.zeroapp.presentation.base.ui_compose_components.days_list.DaysGridShimmer
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.favorites.state.FavoritesSideEffect
import com.example.zeroapp.presentation.favorites.state.FavoritesState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun FavoritesScreen(
    onNavigateToDetail: (Long) -> Unit,
    onNavigateUp: () -> Unit,
    updateAppBar: (AppBarState) -> Unit,
    showDialog: (UIDialog) -> Unit,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.favorites,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                isVisibleBottomBar = false
            ),
        )
    }
    val viewState by favoritesViewModel.collectAsState()

    favoritesViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is FavoritesSideEffect.Dialog -> showDialog(sideEffect.uiDialog)
            is FavoritesSideEffect.NavigationToDayDetail -> onNavigateToDetail(sideEffect.dayId)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = viewState) {
            is FavoritesState.Empty -> {
                Text(text = state.message.asString())
            }

            is FavoritesState.Loaded -> {
                DaysGrid(
                    cellsAmount = state.cellsAmountForGrid,
                    days = state.dayList,
                    onClick = { favoritesViewModel.onClickDay(it) },
                    onLongClick = { favoritesViewModel.onClickLongDay(it) })
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