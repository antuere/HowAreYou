package antuere.how_are_you.presentation.screens.customization.ui_compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import antuere.domain.dto.AppTheme
import antuere.how_are_you.R
import antuere.how_are_you.util.extensions.animatedPagerItem
import com.google.accompanist.pager.HorizontalPagerIndicator


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThemeSelector(
    value: AppTheme,
    onThemeChanged: (AppTheme) -> Unit
) {
    val allThemes = remember {
        AppTheme.values()
    }
    val pagerState = rememberPagerState(
        initialPage = allThemes.indexOf(value),
        initialPageOffsetFraction = 0f,
        pageCount = { allThemes.size }
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onThemeChanged(allThemes[page])
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 32.dp),
            state = pagerState
        ) { page ->
            Image(
                modifier = Modifier.animatedPagerItem(pagerState = pagerState, page = page),
                painter = painterResource(id = R.drawable.card_cat_therapy),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_normal_2)))

        HorizontalPagerIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            pageCount = allThemes.size,
            activeColor = MaterialTheme.colorScheme.primaryContainer
        )
    }
}