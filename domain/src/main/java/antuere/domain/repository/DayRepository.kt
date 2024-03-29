package antuere.domain.repository

import antuere.domain.dto.Day
import kotlinx.coroutines.flow.Flow

interface DayRepository {

    suspend fun getAllDays(): Flow<List<Day>>

    suspend fun getFavoritesDays(): Flow<List<Day>>

    suspend fun getSelectedDays(dayStart: Long, dayEnd: Long): Flow<List<Day>>

    suspend fun getCertainDays(dayStart: Long): Flow<List<Day>>

    suspend fun getDaysByLimit(limit: Int): Flow<List<Day>>

    suspend fun getLastDay(): Flow<Day?>

    suspend fun getDayById(id: Long): Flow<Day?>

    suspend fun deleteDay(id: Long)

    suspend fun deleteAllDaysLocal()

    suspend fun deleteAllDaysRemote(onSuccess: () -> Unit, onFailure: (String?) -> Unit)

    suspend fun insertLocal(day: Day)

    suspend fun insertLocal(days: List<Day>)

    suspend fun insertRemote(day: Day)

    suspend fun insertLocalDaysToRemote()

    suspend fun update(day: Day)

    suspend fun refreshRemoteData()

}