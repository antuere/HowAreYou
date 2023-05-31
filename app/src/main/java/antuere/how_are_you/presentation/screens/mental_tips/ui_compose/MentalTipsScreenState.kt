package antuere.how_are_you.presentation.screens.mental_tips.ui_compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.screens.mental_tips.state.MentalTipsState
import antuere.how_are_you.presentation.screens.mental_tips.ui_compose.components.MentalTipItem
import antuere.how_are_you.util.extensions.animatedPagerItem
import antuere.how_are_you.util.extensions.paddingTopBar
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MentalTipsScreenState(
    viewState: () -> MentalTipsState,
) {
    val pagerState = rememberPagerState()

    when (val state = viewState()) {
        is MentalTipsState.Loaded -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .paddingTopBar(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.1F))
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    pageCount = state.listMentalTips.size,
                    state = pagerState,
                ) { page ->
                    val mentalTip = state.listMentalTips[page]
                    MentalTipItem(
                        modifier = Modifier
                            .animatedPagerItem(pagerState = pagerState, page = page)
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_0)),
                        mentalTip = mentalTip
                    )
                }
                Spacer(modifier = Modifier.weight(0.1F))

                HorizontalPagerIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = dimensionResource(id = R.dimen.padding_normal_0)),
                    pagerState = pagerState,
                    pageCount = state.listMentalTips.size,
                    activeColor = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }

        is MentalTipsState.Loading -> {
            FullScreenProgressIndicator()
        }
    }
}