package antuere.how_are_you.presentation.mental_tips_categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.mental_tips_categories.state.MentalTipsCategoriesIntent
import antuere.how_are_you.presentation.mental_tips_categories.state.MentalTipsCategoriesSideEffect
import antuere.how_are_you.presentation.mental_tips_categories.state.MentalTipsCategoriesState
import antuere.how_are_you.presentation.mental_tips_categories.ui_compose.CategoryCard
import antuere.how_are_you.util.extensions.paddingTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun MentalTipsCategoriesScreen(
    onNavigateToMentalTips: (String) -> Unit,
    viewModel: MentalTipsCategoriesViewModel = hiltViewModel()
) {
    Timber.i("MVI error test : enter in mental tips categories screen")
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.mental_tips,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MentalTipsCategoriesSideEffect.NavigateToMentalTips -> {
                onNavigateToMentalTips(sideEffect.categoryName)
            }
        }
    }

    when (val state = viewState) {
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
                        onClick = {
                            MentalTipsCategoriesIntent.TipsCategorySelected(it)
                                .run(viewModel::onIntent)
                        }
                    )
                }
            }
        }
        MentalTipsCategoriesState.Loading -> {
            FullScreenProgressIndicator()
        }
    }
}
