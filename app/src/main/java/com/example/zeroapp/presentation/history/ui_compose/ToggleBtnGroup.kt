package com.example.zeroapp.presentation.history.ui_compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import antuere.domain.dto.ToggleBtnState
import com.example.zeroapp.R

@Composable
fun ToggleBtnGroup(
    modifier: Modifier = Modifier,
    currentToggleBtnState: ToggleBtnState,
    onClick: (ToggleBtnState) -> Unit
) {

    val shapePercent = 50
    val allDaysBtnName = stringResource(id = R.string.all_days_title)
    val weekBtnName = stringResource(id = R.string.last_week_title)
    val monthBtnName = stringResource(id = R.string.current_month_title)

    val toggleList = mapOf(
        weekBtnName to ToggleBtnState.LAST_WEEK,
        monthBtnName to ToggleBtnState.CURRENT_MONTH,
        allDaysBtnName to ToggleBtnState.ALL_DAYS,
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        toggleList.entries.forEachIndexed { index, entry ->
            OutlinedButton(
                modifier = when (index) {
                    0 ->
                        Modifier
                            .width(dimensionResource(id = R.dimen.checked_button_width))
                            .offset(0.dp, 0.dp)
                            .zIndex(if (currentToggleBtnState == entry.value) 1f else 0f)
                    else ->
                        Modifier
                            .width(dimensionResource(id = R.dimen.checked_button_width))
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (currentToggleBtnState == entry.value) 1f else 0f)
                },
                onClick = {
                    onClick(entry.value)
                },
                shape = when (index) {
                    // left outer button
                    0 -> RoundedCornerShape(
                        topStartPercent = shapePercent,
                        topEndPercent = 0,
                        bottomStartPercent = shapePercent,
                        bottomEndPercent = 0
                    )
                    // right outer button
                    toggleList.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = shapePercent,
                        bottomStartPercent = 0,
                        bottomEndPercent = shapePercent
                    )
                    // middle button
                    else -> RoundedCornerShape(0.dp)
                },
                border = BorderStroke(
                    1.dp, Color.Gray
                ),
                colors = if (currentToggleBtnState == entry.value) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.7f
                        ), contentColor = Color.Black
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = Color.Black
                    )
                },
            ) {
                Text(
                    text = entry.key,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}