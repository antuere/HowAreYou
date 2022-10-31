package antuere.domain.dto

import antuere.domain.util.TimeUtility

data class Day(

    var dayId: Long = TimeUtility.parseCurrentTime().time,

    val imageId: Int,

    var dayText: String,

    val dateString: String = TimeUtility.formatCurrentTime(),

    var isFavorite: Boolean = false

)