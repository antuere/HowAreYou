package com.example.zeroapp.presentation.history.ui_compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.domain.dto.Day
import com.example.zeroapp.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DaysListItem(
    modifier: Modifier = Modifier,
    day: Day,
    onClick: (Day) -> Unit,
    onLongClick: (Day) -> Unit
) {
    Card(
        modifier = modifier
            .combinedClickable(
                onClick = { onClick(day) },
                onLongClick = { onLongClick(day) }
            )
            .padding(dimensionResource(id = R.dimen.padding_small_0)),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Image(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small_0))
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = day.imageResId),
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_small_1))
                .align(Alignment.CenterHorizontally),
            text = day.dateString,
            fontSize = 14.sp
        )

    }
}

@Preview
@Composable
private fun TestDaysListItem() {
    val testDay = Day(
        dayId = 0,
        imageResId = antuere.data.R.drawable.smile_happy,
        dayText = "Test text",
        dateString = "29.11.2022",
        transitionName = "noup",
        isFavorite = false
    )

    DaysListItem(day = testDay, onClick = {}, onLongClick = {})

}