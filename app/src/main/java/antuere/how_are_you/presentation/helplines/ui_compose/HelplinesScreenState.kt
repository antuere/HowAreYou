package antuere.how_are_you.presentation.helplines.ui_compose

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.domain.dto.helplines.SupportedCountry
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.helplines.state.HelplinesIntent
import antuere.how_are_you.presentation.helplines.state.HelplinesState
import antuere.how_are_you.util.extensions.paddingTopBar
import antuere.how_are_you.util.extensions.toStable
import timber.log.Timber

@Composable
fun HelplinesScreenState(
    lazyListState: () -> LazyListState,
    viewState: () -> HelplinesState,
    onIntent: (HelplinesIntent) -> Unit,
) {
    Timber.i("MVI error test : we in parent helplines screen")
    when (val state = viewState()) {
        is HelplinesState.Loading -> {
            FullScreenProgressIndicator()
        }
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

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                    CountrySelectionMenu(
                        modifier = Modifier.fillMaxWidth(0.6F),
                        isExpanded = state.isMenuExpanded,
                        countries = state.supportedCountries,
                        flagId = state.currentFlagId,
                        textFieldValue = state.textFieldValue.asString(),
                        onSelectedCountryChange = { onIntent(HelplinesIntent.CountrySelected(it)) },
                        onExpandedChange = { onIntent(HelplinesIntent.CountyMenuClicked) }
                    )
                } else {
                    CountrySelectionMenuEditable(
                        modifier = Modifier.fillMaxWidth(0.6F),
                        countries = state.supportedCountries,
                        isExpanded = state.isMenuExpanded,
                        textFieldValue = state.textFieldValue.asString(),
                        flagId = state.currentFlagId,
                        onSelectedCountryChange = { onIntent(HelplinesIntent.CountrySelected(it)) },
                        onSelectedCountryFieldChange = { value: String, countriesMap: Map<String, SupportedCountry> ->
                            onIntent(HelplinesIntent.CountryFieldChanged(value, countriesMap))
                        },
                        onExpandedChange = { onIntent(HelplinesIntent.CountyMenuClicked) },
                        onDismiss = { onIntent(HelplinesIntent.CountyMenuDismissed) }
                    )
                }
                Spacer(modifier = Modifier.weight(0.05F))

                HelplinesColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1F),
                    lazyListState = lazyListState(),
                    helplines = state.selectedCountry.helplines.toStable(key = state.selectedCountry),
                    onClickPhone = { phone: String ->
                        onIntent(HelplinesIntent.PhoneClicked(phone))
                    },
                    onClickWebsite = { website: String ->
                        onIntent(HelplinesIntent.WebsiteClicked(website))
                    },
                    onClickItem = { index: Int ->
                        onIntent(HelplinesIntent.HelplineClicked(index))
                    }
                )
            }
        }
    }
}