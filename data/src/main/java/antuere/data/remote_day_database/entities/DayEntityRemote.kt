package antuere.data.remote_day_database.entities

import antuere.domain.util.TimeUtility

data class DayEntityRemote(

        var dayId: Long= 0L,

        val imageId: Int = 0,

        var dayText: String = "",

        val dateString: String = TimeUtility.formatCurrentTime(),

        var isFavorite: Boolean = false

    )
