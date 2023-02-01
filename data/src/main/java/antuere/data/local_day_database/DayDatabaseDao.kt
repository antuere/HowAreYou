package antuere.data.local_day_database

import androidx.room.*
import antuere.data.local_day_database.entities.DayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDatabaseDao : BaseDao<DayEntity> {

    @Query("DELETE FROM day_table")
    fun clear()

    @Query("SELECT * FROM day_table ORDER BY dayId DESC LIMIT 1")
    fun getLastDay(): Flow<DayEntity?>

    @Query("DELETE FROM day_table WHERE dayId = :id ")
    fun deleteDay(id: Long)

    @Query("SELECT * FROM day_table WHERE dayId = :id")
    fun getDayById(id: Long): Flow<DayEntity?>

    @Query("SELECT * FROM day_table ORDER BY dayId DESC")
    fun getAllDays(): Flow<List<DayEntity>>

    @Query("SELECT * FROM day_table WHERE is_favorite = 1 ORDER BY dayId DESC ")
    fun getFavoritesDays(): Flow<List<DayEntity>>

    @Query("SELECT * FROM day_table WHERE dayId BETWEEN :dayStart AND :dayEnd ORDER BY dayId DESC ")
    fun getSelectedDays(dayStart: Long, dayEnd: Long): Flow<List<DayEntity>>

    @Query("SELECT * FROM day_table WHERE dayId >= :dateLong ORDER BY dayId DESC ")
    fun getCertainDays(dateLong: Long): Flow<List<DayEntity>>

    @Query("SELECT * FROM day_table ORDER BY dayId DESC LIMIT :limit ")
    fun getDaysByLimit(limit: Int): Flow<List<DayEntity>>

}
