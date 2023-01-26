package antuere.domain.util

sealed class TimeFormat(val stringFormat: String) {
    object Default : TimeFormat("dd/MM/yy")
    object Dot : TimeFormat("dd.MM.yy")
}