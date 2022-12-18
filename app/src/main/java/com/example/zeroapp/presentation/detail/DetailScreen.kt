package com.example.zeroapp.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog

@Composable
fun DetailScreen(
    onComposing: (AppBarState, Boolean) -> Unit,
    onNavigateUp: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val uiDialog by detailViewModel.uiDialog.collectAsState()
    val selectedDay by detailViewModel.selectedDay.collectAsState()
    val navigateToHistory by detailViewModel.navigateToHistory.collectAsState()

    if (navigateToHistory) {
        onNavigateUp()
        detailViewModel.navigateDone()
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
                            detailViewModel.onClickFavoriteButton()
                            isFavoriteDay = isFavoriteDay.not()
                        }) {
                            Icon(
                                painter = if (isFavoriteDay) painterResource(id = R.drawable.ic_baseline_favorite)
                                else painterResource(id = R.drawable.ic_baseline_favorite_border),
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = { detailViewModel.onClickDeleteButton() }) {
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