package com.example.zeroapp.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.base.ui_compose_components.DaysListItem

@Composable
fun FavoritesScreen(
    onNavigateToDetail: (Long) -> Unit,
    onNavigateUp: () -> Unit,
    updateAppBar: (AppBarState) -> Unit,
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

    val listDays by favoritesViewModel.listDays.collectAsState()
    val uiDialog by favoritesViewModel.uiDialog.collectAsState()
//    val navigateToDetailState by favoritesViewModel.navigateToDetailState.collectAsState()

//    LaunchedEffect(navigateToDetailState) {
//        navigateToDetailState?.let { state ->
//            if (state.navigateToDetail) {
//                onNavigateToDetail(state.dayId!!)
//            }
//            favoritesViewModel.doneNavigateToDetail()
//        }
//    }

//    uiDialog?.let {
//        Dialog(dialog = it)
//    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize()
        ) {
            items(listDays) { day ->
                DaysListItem(
                    day = day,
                    onClick = { favoritesViewModel.onClickDay(it) },
                    onLongClick = { favoritesViewModel.onClickLongDay(it) },
                )
            }
        }
    }
}