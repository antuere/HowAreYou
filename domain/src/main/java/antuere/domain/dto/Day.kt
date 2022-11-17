package antuere.domain.dto

import antuere.domain.util.TimeUtility

data class Day(

    var dayId: Long = TimeUtility.parseCurrentTime().time,

    val imageResId: Int,

    var dayText: String,

    val dateString: String = TimeUtility.formatCurrentTime(),

    val transitionName: String = "transition_name_unique_%s".format(dayId.toString()),

    var isFavorite: Boolean = false

)