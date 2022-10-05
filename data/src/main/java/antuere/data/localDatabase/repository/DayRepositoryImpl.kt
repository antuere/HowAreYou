package antuere.data.localDatabase.repository

import antuere.data.localDatabase.DayDatabase
import antuere.data.localDatabase.mappers.DayEntityMapper
import antuere.domain.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DayRepositoryImpl @Inject constructor(
    private val dayDataBase: DayDatabase,
    private val dayEntityMapper: DayEntityMapper
) : DayRepository {

    override fun getAllDays(): Flow<List<Day>> {
        return dayDataBase.dayDatabaseDao.getAllDays().map {
            it.map { dayEntity ->
                dayEntityMapper.mapToDomainModel(dayEntity)
            }
        }
    }

    override suspend fun getDay(): Day? {
        var result : Day? = null
        withContext(Dispatchers.IO) {
            val dayEntity = dayDataBase.dayDatabaseDao.getDay()
            dayEntity?.let {
                val day = dayEntityMapper.mapToDomainModel(dayEntity)
                result = day
            }
        }
        return result
    }

    override suspend fun getDayById(id: Long): Day? {
        var result : Day? = null
        withContext(Dispatchers.IO) {
            val dayEntity = dayDataBase.dayDatabaseDao.getDayById(id)
            dayEntity?.let {
                val day = dayEntityMapper.mapToDomainModel(dayEntity)
                result = day
            }
        }
        return result
    }

    override suspend fun deleteDay(id: Long) {
        withContext(Dispatchers.IO) {
            dayDataBase.dayDatabaseDao.deleteDay(id)
        }
    }

    override suspend fun deleteAllDays() {
        withContext(Dispatchers.IO) {
            dayDataBase.dayDatabaseDao.clear()
        }
    }

    override suspend fun insert(day: Day) {
        withContext(Dispatchers.IO) {
            val dayEntity = dayEntityMapper.mapFromDomainModel(day)
            dayDataBase.dayDatabaseDao.insert(dayEntity)
        }
    }
}