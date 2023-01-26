package antuere.how_are_you.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.home.ui_compose.CardWithQuote
import antuere.how_are_you.presentation.base.ui_compose_components.card.CardDefault
import antuere.how_are_you.presentation.base.ui_compose_components.card.CardWithOnClick
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_theme.TealMain
import antuere.how_are_you.presentation.home.state.HomeIntent
import antuere.how_are_you.presentation.home.state.HomeSideEffect
import antuere.how_are_you.presentation.home.state.HomeState
import antuere.how_are_you.util.paddingBotAndTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun HomeScreen(
    onNavigateToMentalTips: () -> Unit,
    onNavigateToHelpForYou: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToCats: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToAddDay: () -> Unit,
    viewModel: () -> HomeViewModel,
) {

    Timber.i("MVI error test : enter in home screen")
    val context = LocalContext.current
    val appState = LocalAppState.current
    val viewState by viewModel().collectAsState()

    viewModel().collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.Dialog -> {
                appState.showDialog(sideEffect.uiDialog)
            }
            HomeSideEffect.NavigationToAddDay -> {
                onNavigateToAddDay()
            }
            is HomeSideEffect.NavigationToDayDetail -> {
                onNavigateToDetail(sideEffect.dayId)
            }
            is HomeSideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }
        }

    }

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.home,
                isVisibleBottomBar = true
            ),
        )
        appState.dismissSnackbar()
    }

    when (val state = viewState) {
        HomeState.Loading -> {
            FullScreenProgressIndicator()
        }

        is HomeState.Loaded -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_normal_0))
                    .paddingBotAndTopBar(),
                verticalArrangement = Arrangement.Top
            ) {
                CardWithQuote(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.3F),
                    titleText = stringResource(
                        id = R.string.quotes_title
                    ),
                    quoteText = state.quoteText,
                    quiteAuthor = state.quoteAuthor
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.2F),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardWithOnClick(
                        onClick = onNavigateToMentalTips,
                        cardModifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                end = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = R.string.mental_tips)
                    )
                    CardWithOnClick(
                        onClick = onNavigateToHelpForYou,
                        cardModifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                start = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = R.string.help_for_you)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.2F),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardWithOnClick(
                        onClick = onNavigateToFavorites,
                        cardModifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                end = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = R.string.favorites),
                    )
                    CardWithOnClick(
                        onClick = onNavigateToCats,
                        cardModifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                start = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = R.string.cats)
                    )
                }

                CardDefault(
                    cardModifier = Modifier
                        .fillMaxSize()
                        .weight(0.3F)
                        .padding(top = dimensionResource(id = R.dimen.padding_small_1)),
                    textModifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_normal_1),
                        start = dimensionResource(id = R.dimen.padding_normal_1),
                        end = dimensionResource(id = R.dimen.padding_normal_1)
                    ),
                    titleText = state.wishText.asString(),
                    textAlignment = Alignment.TopStart
                ) {
                    Spacer(modifier = Modifier.weight(1F))

                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(
                                bottom = dimensionResource(id = R.dimen.padding_normal_1),
                                start = dimensionResource(id = R.dimen.padding_normal_1),
                                end = dimensionResource(id = R.dimen.padding_normal_1)
                            ),
                        onClick = { HomeIntent.FabClicked.run(viewModel()::onIntent) },
                        containerColor = TealMain
                    ) {
                        Icon(
                            painter = painterResource(id = state.fabButtonState.image),
                            modifier = Modifier.size(24.dp),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}
