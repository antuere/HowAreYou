package com.example.zeroapp.presentation.history.ui_compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import antuere.domain.dto.Day
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.history.MyAnalystForHistory

@Composable
fun HistoryHeaderText(
    modifier: Modifier = Modifier,
    dayList: List<Day>,
) {
    val firstDate = TimeUtility.parseLongToCalendar(dayList.first().dayId)
    val lastDate = TimeUtility.parseLongToCalendar(dayList.last().dayId)

    val title = MyAnalystForHistory.getHeaderForHistory(firstDate, lastDate)

    Text(
        text = title,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_normal_0)),
        textAlign = TextAlign.Center
    )
}
