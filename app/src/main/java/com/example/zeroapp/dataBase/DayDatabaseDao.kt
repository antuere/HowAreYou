package com.example.zeroapp.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DayDatabaseDao {

    @Insert
    fun insert(day: Day)

    @Update
    fun update(day: Day)

    @Query("DELETE FROM day_table")
    fun clear()

    @Query("SELECT * FROM day_table ORDER BY dayId DESC LIMIT 1")
    fun getDay(): Day?

    @Query("DELETE FROM day_table WHERE dayId = :id ")
    fun deleteDay(id: Long)

    @Query("SELECT * FROM day_table WHERE dayId = :id")
    fun getDayById(id: Long): Day?

    @Query("SELECT * FROM day_table ORDER BY dayId DESC")
    fun getAllDays(): LiveData<List<Day>>

}