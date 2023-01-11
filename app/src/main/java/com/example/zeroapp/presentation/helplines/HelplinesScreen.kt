package com.example.zeroapp.presentation.helplines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.helplines.ui_compose.ExposedDropdownMenu
import com.example.zeroapp.presentation.helplines.ui_compose.HelplineItem

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

    val listCountry by helplinesViewModel.countries.collectAsState()
    val selectedCountry by helplinesViewModel.selectedCountry.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.padding_normal_0)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.05F))
        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(0.6F),
            countries = listCountry,
            selectedCountry = selectedCountry!!,
            onSelectedCountryChange = { helplinesViewModel.onCountrySelected(it) }
        )
        Spacer(modifier = Modifier.weight(0.05F))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = selectedCountry!!.helplines) { helpline ->
                HelplineItem(
                    modifier = Modifier
                        .fillMaxWidth(0.85F)
                        .padding(dimensionResource(id = R.dimen.padding_normal_0)),
                    helpline = helpline
                )
            }
        }
    }
}