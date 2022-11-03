package antuere.data.preferences_data_store.entities


sealed class ToggleBtnStateEntity(val id: Int) {

    data class AllDays(val _id: Int) : ToggleBtnStateEntity(_id)

    data class LastWeek(val _id: Int) : ToggleBtnStateEntity(_id)

    data class CurrentMonth(val _id: Int) : ToggleBtnStateEntity(_id)
}

