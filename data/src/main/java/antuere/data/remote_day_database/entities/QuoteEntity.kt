package antuere.data.remote_day_database.entities

import androidx.annotation.Keep

@Keep
data class QuoteEntity(

    var text: String = "",

    var author: String = ""
)