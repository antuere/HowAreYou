package antuere.how_are_you.presentation.screens.mental_tips_categories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.mental_tips_categories.state.MentalTipsCategoriesSideEffect
import antuere.how_are_you.presentation.screens.mental_tips_categories.ui_compose.MentalTipsCategoriesScreenState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun MentalTipsCategoriesScreen(
    onNavigateToMentalTips: (String) -> Unit,
    viewModel: MentalTipsCategoriesViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in mental tips categories screen")
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    appState.DisableBackBtnWhileTransitionAnimate()
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

    MentalTipsCategoriesScreenState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) }
    )
}
