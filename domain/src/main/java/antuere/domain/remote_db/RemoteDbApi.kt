package antuere.domain.remote_db

import antuere.domain.dto.Day

interface RemoteDbApi {

    suspend fun getDays(): List<Day>

    suspend fun deleteDay(id: Long)

    suspend fun deleteAllDays()

    suspend fun insert(day: Day)

    suspend fun insert(days: List<Day>)

    suspend fun update(day: Day)
}