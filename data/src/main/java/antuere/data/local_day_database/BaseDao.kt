package antuere.data.local_day_database

import androidx.room.*

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<T>): List<Long>

    @Update
    fun update(obj: T)

    @Upsert
    fun upsert(obj: T): Long

    @Upsert
    fun upsert(obj: List<T>): List<Long>

    @Delete
    fun delete(obj: T)

    @Delete
    fun delete(vararg obj: T)
}