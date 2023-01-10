package com.example.zeroapp.presentation.helplines

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState

@Composable
fun HelplinesScreen(
    updateAppBar: (AppBarState) -> Unit,
    onNavigateUp: () -> Unit,
    helplinesViewModel: HelplinesViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.helplines,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                isVisibleBottomBar = false
            ),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_normal_0)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.05F))


    }


}