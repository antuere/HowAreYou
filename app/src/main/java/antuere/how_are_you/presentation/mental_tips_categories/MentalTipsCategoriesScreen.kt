package antuere.how_are_you.presentation.mental_tips_categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.mental_tips_categories.ui_compose.CategoryCard
import antuere.how_are_you.util.paddingTopBar

@Composable
fun MentalTipsCategoriesScreen(
    onNavigateUp: () -> Unit,
    onNavigateToMentalTip: (String) -> Unit,
    updateAppBar: (AppBarState) -> Unit,
    mentalTipsViewModel: MentalTipsCategoriesViewModel = hiltViewModel()
) {
    val listCategories by mentalTipsViewModel.listMentalTipsCategories.collectAsState()

    LaunchedEffect(true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.mental_tips,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                isVisibleBottomBar = false
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_normal_0))
            .paddingTopBar()
    ) {
        listCategories.forEachIndexed { i, tipsCategory ->

            var paddingTop = dimensionResource(id = R.dimen.padding_small_0)
            if (i == 0) {
                paddingTop = 0.dp
            }

            var paddingBot = dimensionResource(id = R.dimen.padding_small_0)
            if (i == listCategories.size - 1) {
                paddingBot = 0.dp
            }

            CategoryCard(
                modifier = Modifier
                    .padding(top = paddingTop, bottom = paddingBot)
                    .weight(1F),
                category = tipsCategory,
                onClick = { onNavigateToMentalTip(it) }
            )
        }
    }
}
