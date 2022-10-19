package antuere.data.remoteDataBase.entities

import antuere.data.remoteDataBase.converters.ConvertersRemote
import antuere.domain.util.TimeUtility

data class DayEntityRemote(

        var dayId: Long= 0L,

        val currentDate: Long = ConvertersRemote.calendarToLong(TimeUtility.calendar)!!,

        val imageId: Int = 0,

        var dayText: String = "",

        val currentDateString: String = TimeUtility.formatCurrentTime(),

        var isFavorite: Boolean = false

    )
