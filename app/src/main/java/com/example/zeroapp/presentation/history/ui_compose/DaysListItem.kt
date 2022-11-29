package com.example.zeroapp.presentation.history.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import antuere.domain.dto.Day
import com.example.zeroapp.R

@Composable
fun DaysListItem(
    modifier: Modifier = Modifier,
    day: Day,
    onClick: (Day) -> Unit
) {
    Card(
        modifier = modifier.clickable {
            onClick(day)
        },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Image(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small_0)),
            painter = painterResource(id = day.imageResId),
            contentDescription = null,
            alignment = Alignment.Center
        )
        Text(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.padding_small_1)),
            textAlign = TextAlign.Center,
            text = day.dateString
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

    DaysListItem(day = testDay, onClick = {})

}