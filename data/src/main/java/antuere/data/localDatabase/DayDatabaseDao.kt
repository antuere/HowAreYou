package antuere.data.localDatabase

import androidx.room.*
import antuere.data.localDatabase.entities.DayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDatabaseDao {

    @Insert
    fun insert(dayEntity: DayEntity)

    @Update
    fun update(dayEntity: DayEntity)

    @Query("DELETE FROM day_table")
    fun clear()

    @Query("SELECT * FROM day_table ORDER BY dayId DESC LIMIT 1")
    fun getDay(): DayEntity?

    @Query("DELETE FROM day_table WHERE dayId = :id ")
    fun deleteDay(id: Long)

    @Query("SELECT * FROM day_table WHERE dayId = :id")
    fun getDayById(id: Long): DayEntity?

    @Query("SELECT * FROM day_table ORDER BY dayId DESC")
    fun getAllDays(): Flow<List<DayEntity>>

}
