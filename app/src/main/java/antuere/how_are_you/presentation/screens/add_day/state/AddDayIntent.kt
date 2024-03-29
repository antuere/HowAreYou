package antuere.how_are_you.presentation.screens.add_day.state


sealed interface AddDayIntent {
    class SmileClicked(val imageResId: Int) : AddDayIntent
    class DayDescChanged(val value: String) : AddDayIntent
}