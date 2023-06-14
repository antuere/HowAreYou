package antuere.how_are_you.presentation.screens.home.ui_compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.card.CardWithFab
import antuere.how_are_you.presentation.base.ui_compose_components.card.CardWithImage
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.screens.home.state.HomeIntent
import antuere.how_are_you.presentation.screens.home.state.HomeState
import antuere.how_are_you.presentation.screens.home.ui_compose.components.CardWithQuote
import antuere.how_are_you.util.extensions.paddingBotAndTopBar

@Composable
fun HomeScreenContent(
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
                    titleText = stringResource(R.string.quotes_title),
                    quoteText = state.quoteText,
                    quiteAuthor = state.quoteAuthor
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.2F),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardWithImage(
                        imageRes = R.drawable.card_tips,
                        onClick = { onIntent(HomeIntent.MentalTipsClicked) },
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                end = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = R.string.mental_tips),
                        imageAlignment = Alignment.TopCenter
                    )
                    CardWithImage(
                        imageRes = R.drawable.card_help,
                        onClick = { onIntent(HomeIntent.HelpForYouClicked) },
                        modifier = Modifier
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
                    CardWithImage(
                        imageRes = R.drawable.card_favorites,
                        onClick = { onIntent(HomeIntent.FavoritesClicked) },
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                end = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = R.string.favorites),
                    )
                    CardWithImage(
                        imageRes = R.drawable.card_cat_therapy,
                        onClick = { onIntent(HomeIntent.CatsClicked) },
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                start = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = R.string.cats)
                    )
                }

                CardWithFab(
                    modifier = Modifier
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
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            painter = painterResource(id = state.fabButtonState.image),
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}