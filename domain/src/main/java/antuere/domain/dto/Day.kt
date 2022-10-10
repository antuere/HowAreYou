package antuere.domain.dto

import antuere.domain.util.TimeUtility
import java.util.*


data class Day(

    var dayId: Long = 0L,

    val currentDate: Calendar = TimeUtility.calendar,

    val imageId: Int,

    var dayText: String,

    val currentDateString: String = TimeUtility.formatCurrentTime()

)