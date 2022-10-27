package antuere.domain.repository

import antuere.domain.dto.Day
import kotlinx.coroutines.flow.Flow

interface DayRepository {

    suspend fun getAllDays(): Flow<List<Day>>

    suspend fun getFavoritesDays(): Flow<List<Day>>

    suspend fun getSelectedDays(dayStart: Long, dayEnd: Long): Flow<List<Day>>

    suspend fun getRequiresDays(dateLong: Long): Flow<List<Day>>

    suspend fun getDay(): Day?

    suspend fun getDayById(id: Long): Day?

    suspend fun deleteDay(id: Long)

    suspend fun deleteAllDays()

    suspend fun insert(day: Day)

    suspend fun update(day: Day)

    fun refreshRemoteData()


}