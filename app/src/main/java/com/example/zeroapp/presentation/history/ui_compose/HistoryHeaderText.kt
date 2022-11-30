package com.example.zeroapp.presentation.history.ui_compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import antuere.domain.dto.Day
import antuere.domain.util.TimeUtility
import com.example.zeroapp.presentation.history.MyAnalystForHistory

@Composable
fun HistoryHeaderText(
    modifier: Modifier = Modifier,
    dayList: List<Day>,
    myAnalystForHistory: MyAnalystForHistory
) {
    if (dayList.isNotEmpty()) {
        val firstDate = TimeUtility.parseLongToCalendar(dayList.first().dayId)
        val lastDate = TimeUtility.parseLongToCalendar(dayList.last().dayId)

        val title = myAnalystForHistory.getHeaderForHistory(firstDate, lastDate)

        Text(text = title, modifier = modifier, textAlign = TextAlign.Center)
    }
}
