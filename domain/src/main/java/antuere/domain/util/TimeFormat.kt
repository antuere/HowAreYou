package antuere.domain.util

sealed class TimeFormat(val stringFormat: String) {
    object Default : TimeFormat("dd/MM/yy")
    object MonthNamed : TimeFormat("d MMM yyyy")
    object Dot : TimeFormat("dd.MM.yy")
}