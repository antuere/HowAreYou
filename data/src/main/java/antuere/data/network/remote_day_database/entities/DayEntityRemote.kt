package antuere.data.network.remote_day_database.entities

import androidx.annotation.Keep
import antuere.domain.util.TimeUtility

@Keep
data class DayEntityRemote(

        var dayId: Long= 0L,

        val imageName: String = "",

        var dayText: String = "",

        val dateString: String = TimeUtility.formatCurrentTime(),

        var isFavorite: Boolean = false

    )
