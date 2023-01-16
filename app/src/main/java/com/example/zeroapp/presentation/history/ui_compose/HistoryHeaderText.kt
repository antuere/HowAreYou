package com.example.zeroapp.presentation.history.ui_compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import antuere.domain.dto.Day
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.history.MyAnalystForHistory
import timber.log.Timber

@Composable
fun HistoryHeaderText(
    modifier: Modifier = Modifier,
    dayList: List<Day>,
) {

//    TODO вынести логику в vm
    val context = LocalContext.current
    var title by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        val firstDate = TimeUtility.parseLongToCalendar(dayList.first().dayId)
        val lastDate = TimeUtility.parseLongToCalendar(dayList.last().dayId)
        title = MyAnalystForHistory.getHeaderForHistory(firstDate, lastDate, context)
    }

    Text(
        text = title,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_normal_0)),
        textAlign = TextAlign.Center
    )
}
