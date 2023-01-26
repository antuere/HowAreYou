package antuere.how_are_you.presentation.mental_tips

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.mental_tips.ui_compose.MentalTipItem
import antuere.how_are_you.util.paddingTopBar
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MentalTipsScreen(
    mentalTipsViewModel: MentalTipsViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val titleId by mentalTipsViewModel.screenLabelId.collectAsState()
    val listMentalTips by mentalTipsViewModel.listMentalTips.collectAsState()
    val appState = LocalAppState.current

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = titleId,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = appState::navigateUp,
                isVisibleBottomBar = false
            ),
        )
    }

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
            count = listMentalTips.size,
            state = pagerState,
        ) { page ->
            val mentalTip = listMentalTips[page]
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