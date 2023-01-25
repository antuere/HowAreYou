package antuere.how_are_you.presentation.helplines

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.helplines.state.HelplinesState
import antuere.how_are_you.presentation.helplines.ui_compose.CountrySelectionMenu
import antuere.how_are_you.presentation.helplines.ui_compose.HelplineItem
import antuere.how_are_you.util.paddingTopBar
import org.orbitmvi.orbit.compose.collectAsState

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

    val viewState by helplinesViewModel.collectAsState()

    when (val state = viewState) {
        is HelplinesState.Loaded -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .paddingTopBar()
                    .padding(top = dimensionResource(id = R.dimen.padding_normal_0)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.05F))
                CountrySelectionMenu(
                    modifier = Modifier.fillMaxWidth(0.6F),
                    countries = state.supportedCountries,
                    selectedCountry = state.selectedCountry,
                    onSelectedCountryChange = { helplinesViewModel.onCountrySelected(it) }
                )
                Spacer(modifier = Modifier.weight(0.05F))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items = state.selectedCountry.helplines) { helpline ->
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
        is HelplinesState.Loading -> {
            FullScreenProgressIndicator()
        }
    }
}