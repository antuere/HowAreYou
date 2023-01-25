package antuere.how_are_you.presentation.add_day.state

import antuere.domain.dto.Day

sealed interface AddDayIntent {
    class SmileClicked(imageResId: Int, descDay: String) : AddDayIntent{
        val day = Day(imageResId = imageResId, dayText = descDay)
    }
    class DayDescChanged(val value: String): AddDayIntent
}