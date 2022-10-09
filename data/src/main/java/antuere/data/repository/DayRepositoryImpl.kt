package antuere.data.repository

import antuere.data.room.DayDatabase
import antuere.data.room.mapping.DayEntityMapper
import antuere.domain.dto.Day
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

    override fun getAllDays(): Flow<List<Day>> = dayDataBase.dayDatabaseDao.getAllDays().map {
        it.map(dayEntityMapper::mapToDomainModel)
    }


    override suspend fun getDay(): Day? = withContext(Dispatchers.IO) {
        val dayEntity = dayDataBase.dayDatabaseDao.getDay()
        dayEntity?.let(dayEntityMapper::mapToDomainModel)
    }


    override suspend fun getDayById(id: Long): Day? = withContext(Dispatchers.IO) {
        val dayEntity = dayDataBase.dayDatabaseDao.getDayById(id)
        dayEntity?.let(dayEntityMapper::mapToDomainModel)
    }

    override suspend fun deleteDay(id: Long) = withContext(Dispatchers.IO) {
        dayDataBase.dayDatabaseDao.deleteDay(id)
    }

    override suspend fun deleteAllDays() = withContext(Dispatchers.IO) {
        dayDataBase.dayDatabaseDao.clear()
    }

    override suspend fun insert(day: Day) = withContext(Dispatchers.IO) {
        val dayEntity = dayEntityMapper.mapFromDomainModel(day)
        dayDataBase.dayDatabaseDao.insert(dayEntity)
    }

}