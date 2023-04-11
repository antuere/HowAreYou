package antuere.how_are_you.presentation.base.ui_compose_components.days_list

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import antuere.domain.dto.Day
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DaysGrid(
    cellsAmount: Int,
    days: List<Day>,
    onClick: (Day) -> Unit,
    onLongClick: (Day) -> Unit,
    lazyGridState: LazyGridState = rememberLazyGridState(),
) {
    Timber.i("MVI error test : composed in daysGrid ${days.size}")

    LazyVerticalGrid(
        columns = GridCells.Fixed(cellsAmount),
        modifier = Modifier.fillMaxSize(),
        state = lazyGridState
    ) {
        items(
            items = days,
            key = { it.dayId }
        ) { day ->
            DaysListItem(
                modifier = Modifier
                    .animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 200
                        )
                    ),
                day = day,
                onClick = onClick,
                onLongClick = onLongClick,
            )
        }
    }
}