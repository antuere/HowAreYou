package com.example.zeroapp.presentation.mental_tips

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentalTipsScreen(
    onNavigateUp: () -> Unit,
    onComposing: (AppBarState, Boolean) -> Unit,
    mentalTipsViewModel: MentalTipsViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        onComposing(
            AppBarState(
                titleId = R.string.mental_tips,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() }
            ),
            false
        )
    }

    val listCategories by mentalTipsViewModel.listMentalTipsCategories.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(listCategories) { category ->
            ListItem(
                leadingContent = {
                    Image(
                        painter = painterResource(id = category.iconRes),
                        contentDescription = null
                    )
                },
                headlineText = {
                    Text(text = stringResource(id = category.textRes))
                }
            )
        }
    }
}
