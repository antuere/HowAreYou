package antuere.domain.dto

import antuere.domain.util.TimeUtility
import java.util.*


data class Day(

    var dayId: Long = TimeUtility.currentDate.time,

    val imageId: Int,

    var dayText: String,

    val dateString: String = TimeUtility.formatCurrentTime(),

    var isFavorite: Boolean = false

)