package com.example.zeroapp.presentation.mental_tips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.mental_tips_categories.MentalTipsCategoriesViewModel

@Composable
fun MentalTipsScreen(
    onNavigateUp: () -> Unit,
    onComposing: (AppBarState, Boolean) -> Unit,
    mentalTipsViewModel: MentalTipsViewModel = hiltViewModel()
) {
    val titleId by mentalTipsViewModel.screenLabelId.collectAsState()
    val listMentalTips by mentalTipsViewModel.listMentalTips.collectAsState()

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = titleId,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() }
            ),
            false
        )
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
    }

}