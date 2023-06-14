package antuere.how_are_you.presentation.screens.mental_tips_categories.ui_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.screens.mental_tips_categories.ui_compose.components.CategoryCard
import antuere.how_are_you.presentation.screens.mental_tips_categories.state.MentalTipsCategoriesIntent
import antuere.how_are_you.presentation.screens.mental_tips_categories.state.MentalTipsCategoriesState
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun MentalTipsCategoriesScreenContent(
    viewState: () -> MentalTipsCategoriesState,
    onIntent: (MentalTipsCategoriesIntent) -> Unit,
) {
    when (val state = viewState()) {
        is MentalTipsCategoriesState.Loaded -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_normal_0))
                    .paddingTopBar()
            ) {
                state.listCategories.forEachIndexed { i, tipsCategory ->

                    var paddingTop = dimensionResource(id = R.dimen.padding_small_0)
                    if (i == 0) {
                        paddingTop = 0.dp
                    }

                    var paddingBot = dimensionResource(id = R.dimen.padding_small_0)
                    if (i == state.listCategories.size - 1) {
                        paddingBot = 0.dp
                    }

                    CategoryCard(
                        modifier = Modifier
                            .padding(top = paddingTop, bottom = paddingBot)
                            .weight(1F),
                        category = tipsCategory,
                        onClick = { onIntent(MentalTipsCategoriesIntent.TipsCategorySelected(it)) }
                    )
                }
            }
        }
        MentalTipsCategoriesState.Loading -> {
            FullScreenProgressIndicator()
        }
    }
}