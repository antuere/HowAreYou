package antuere.data.remoteDataBase.converters

import java.util.*

class ConvertersRemote {

    companion object {
        fun calendarFromLong(value: Long?): Calendar? = value?.let { value_new ->
            GregorianCalendar().also { calendar ->
                calendar.timeInMillis = value_new
            }
        }

        fun calendarToLong(timestamp: Calendar?): Long? = timestamp?.timeInMillis
    }
}