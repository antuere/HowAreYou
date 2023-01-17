package com.example.zeroapp.presentation.base.ui_compose_components.days_list

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import antuere.domain.dto.Day
import com.example.zeroapp.presentation.base.ui_compose_components.days_list.DaysListItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DaysGrid(
    cellsAmount: Int,
    days: List<Day>,
    onClick: (Day) -> Unit,
    onLongClick: (Day) -> Unit

) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(cellsAmount),
        modifier = Modifier.fillMaxSize()
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