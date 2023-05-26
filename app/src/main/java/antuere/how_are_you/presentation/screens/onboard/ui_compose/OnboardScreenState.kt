package antuere.how_are_you.presentation.screens.onboard.ui_compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.screens.onboard.state.OnboardIntent
import antuere.how_are_you.presentation.screens.onboard.state.OnboardState
import antuere.how_are_you.presentation.screens.onboard.ui_compose.components.EnterInAppButton
import antuere.how_are_you.presentation.screens.onboard.ui_compose.components.OnboardPageItem
import antuere.how_are_you.util.extensions.animatedPagerItem
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardScreenState(
    viewState: () -> OnboardState,
    onIntent: (OnboardIntent) -> Unit,
) {
    val pagerState = rememberPagerState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            pageCount = viewState().pages.size,
            state = pagerState,
        ) { page ->
            val onboardPage = viewState().pages[page]
            OnboardPageItem(
                modifier = Modifier
                    .animatedPagerItem(pagerState = pagerState, page = page)
                    .fillMaxSize(),
                onboardPage = onboardPage
            )
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            pageCount = viewState().pages.size,
            activeColor = MaterialTheme.colorScheme.primaryContainer
        )
        EnterInAppButton(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = dimensionResource(id = R.dimen.padding_normal_1)),
            isVisible = pagerState.currentPage == viewState().pages.lastIndex,
            onClick = { onIntent(OnboardIntent.EnterInAppClicked) })
    }
}