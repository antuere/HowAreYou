package antuere.how_are_you.presentation.mental_tips

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.mental_tips.state.MentalTipsState
import antuere.how_are_you.presentation.mental_tips.ui_compose.MentalTipItem
import antuere.how_are_you.util.paddingTopBar
import com.google.accompanist.pager.*
import org.orbitmvi.orbit.compose.collectAsState
import timber.log.Timber
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MentalTipsScreen(
    viewModel: MentalTipsViewModel = hiltViewModel()
) {
    Timber.i("MVI error test : enter in mental tips screen")
    val pagerState = rememberPagerState()
    val appState = LocalAppState.current

    val viewState by viewModel.collectAsState()

    LaunchedEffect(viewState.appBarTitleId) {
        appState.updateAppBar(
            AppBarState(
                titleId = viewState.appBarTitleId,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    when (val state = viewState) {
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
                    count = state.listMentalTips.size,
                    state = pagerState,
                ) { page ->
                    val mentalTip = state.listMentalTips[page]
                    MentalTipItem(
                        modifier = Modifier
                            .graphicsLayer {
                                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                                lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }

                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_0)),
                        mentalTip = mentalTip
                    )
                }
                Spacer(modifier = Modifier.weight(0.1F))

                HorizontalPagerIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = dimensionResource(id = R.dimen.padding_normal_0)),
                    pagerState = pagerState
                )
            }
        }
        is MentalTipsState.Loading -> {
            FullScreenProgressIndicator()
        }
    }
}