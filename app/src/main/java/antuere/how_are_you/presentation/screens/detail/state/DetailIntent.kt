package antuere.how_are_you.presentation.screens.detail.state

sealed interface DetailIntent {
    object FavoriteBtnClicked : DetailIntent
    object DeleteBtnClicked : DetailIntent
}