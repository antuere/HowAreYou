package antuere.domain.dto


sealed class ToggleBtnState(val id: Int) {

    data class AllDays(val _id: Int) : ToggleBtnState(_id)

    data class LastWeek(val _id: Int) : ToggleBtnState(_id)

    data class CurrentMonth(val _id: Int) : ToggleBtnState(_id)
}
