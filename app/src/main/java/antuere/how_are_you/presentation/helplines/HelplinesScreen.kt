package antuere.how_are_you.presentation.helplines

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.helplines.state.HelplinesIntent
import antuere.how_are_you.presentation.helplines.state.HelplinesSideEffect
import antuere.how_are_you.presentation.helplines.state.HelplinesState
import antuere.how_are_you.presentation.helplines.ui_compose.CountrySelectionMenu
import antuere.how_are_you.presentation.helplines.ui_compose.HelplineItem
import antuere.how_are_you.util.paddingTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun HelplinesScreen(
    viewModel: HelplinesViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in helplines screen")
    val appState = LocalAppState.current
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val viewState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HelplinesSideEffect.NavigateToDialNumber -> {
                val phoneUri = Uri.parse("tel:" + sideEffect.phoneNumber)
                val intent = Intent(Intent.ACTION_DIAL, phoneUri)
                try {
                    context.startActivity(intent)
                } catch (s: SecurityException) {
                    appState.showSnackbar(s.message ?: "Security exception")
                }
            }
            is HelplinesSideEffect.NavigateToWebsite -> {
                uriHandler.openUri(sideEffect.website)
            }
        }
    }

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.helplines,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

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
                    onSelectedCountryChange = {
                        HelplinesIntent.CountrySelected(it).run(viewModel::onIntent)
                    }
                )
                Spacer(modifier = Modifier.weight(0.05F))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        items = state.selectedCountry.helplines,
                        key = { it.nameResId }
                    ) { helpline ->
                        HelplineItem(
                            helpline = helpline,
                            onClickPhone = { phone: String ->
                                HelplinesIntent.PhoneClicked(phone).run(viewModel::onIntent)
                            },
                            onClickWebsite = { website: String ->
                                HelplinesIntent.WebsiteClicked(website).run(viewModel::onIntent)
                            },
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