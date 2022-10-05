package antuere.domain

import antuere.data.localDatabase.util.getString
import java.util.*


data class Day(

    var dayId: Long = 0L,

    val currentDate: Calendar = Calendar.getInstance(),

    val imageId: Int,

    var dayText: String,

    val currentDateString: String = currentDate.getString()

)