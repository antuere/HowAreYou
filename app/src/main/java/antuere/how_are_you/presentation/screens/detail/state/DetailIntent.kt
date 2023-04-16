package antuere.how_are_you.presentation.screens.detail.state

sealed interface DetailIntent {
    object FavoriteBtnClicked : DetailIntent
    object DeleteBtnClicked : DetailIntent
    object SaveBtnClicked : DetailIntent
    object EditModeOn : DetailIntent
    object EditModeOff : DetailIntent
    class DayDescChanged(val value: String) : DetailIntent
}