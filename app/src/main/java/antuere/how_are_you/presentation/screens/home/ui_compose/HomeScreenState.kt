package antuere.how_are_you.presentation.screens.home.ui_compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.card.CardDefault
import antuere.how_are_you.presentation.base.ui_compose_components.card.CardWithOnClick
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_theme.TealMain
import antuere.how_are_you.presentation.screens.home.state.HomeIntent
import antuere.how_are_you.presentation.screens.home.state.HomeState
import antuere.how_are_you.presentation.screens.home.ui_compose.components.CardWithQuote
import antuere.how_are_you.util.extensions.paddingBotAndTopBar

@Composable
fun HomeScreenState(
    viewState: () -> HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    when (val state = viewState()) {
        is HomeState.Loading -> {
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
                        onClick = { onIntent(HomeIntent.MentalTipsClicked) },
                        cardModifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                end = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = R.string.mental_tips)
                    )
                    CardWithOnClick(
                        onClick = { onIntent(HomeIntent.HelpForYouClicked) },
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
                        onClick = { onIntent(HomeIntent.FavoritesClicked) },
                        cardModifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                end = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = R.string.favorites),
                    )
                    CardWithOnClick(
                        onClick = { onIntent(HomeIntent.CatsClicked) },
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
                        onClick = { onIntent(HomeIntent.FabClicked) },
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