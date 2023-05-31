package antuere.how_are_you.presentation.base.ui_compose_components.days_grid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import antuere.domain.dto.Day
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults
import antuere.how_are_you.util.rememberDaysGradientCache

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DaysGrid(
    cellsAmount: Int,
    days: List<Day>,
    onClick: (Day) -> Unit,
    onLongClick: (Day) -> Unit,
    lazyGridState: LazyGridState = rememberLazyGridState(),
) {
    val gradientCache = rememberDaysGradientCache()

    LazyVerticalGrid(
        columns = GridCells.Fixed(cellsAmount),
        modifier = Modifier.fillMaxSize(),
        state = lazyGridState
    ) {
        items(
            items = days,
            key = { it.dayId }
        ) { day ->
            DaysGridItem(
                modifier = Modifier.animateItemPlacement(),
                day = day,
                onClick = onClick,
                onLongClick = onLongClick,
                gradient = gradientCache[day.imageResId] ?: GradientDefaults.surface()
            )
        }
    }
}