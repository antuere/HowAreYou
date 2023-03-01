package antuere.data.repository

import antuere.data.local_day_database.DayDatabase
import antuere.data.local_day_database.mapping.DayEntityMapper
import antuere.data.remote.remote_day_database.FirebaseRealtimeDB
import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


class DayRepositoryImpl @Inject constructor(
    private val dayDataBaseRoom: DayDatabase,
    private val firebaseRealtimeDB: FirebaseRealtimeDB,
    private val dayEntityMapper: DayEntityMapper,
) : DayRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            refreshRemoteData()
        }
    }

    override suspend fun refreshRemoteData() {
        val daysFromServer = firebaseRealtimeDB.getDays()
        if (daysFromServer.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                deleteAllDaysLocal()
                daysFromServer.forEach { day ->
                    insertLocal(day)
                }
            }
        }
    }

    override suspend fun getAllDays(): Flow<List<Day>> {
        return dayDataBaseRoom.dayDatabaseDao.getAllDays().map {
            it.map { dayEntity ->
                dayEntityMapper.mapToDomainModel(dayEntity)
            }
        }
    }

    override suspend fun getFavoritesDays(): Flow<List<Day>> {
        return dayDataBaseRoom.dayDatabaseDao.getFavoritesDays().map {
            it.map { dayEntity ->
                dayEntityMapper.mapToDomainModel(dayEntity)
            }
        }
    }

    override suspend fun getSelectedDays(dayStart: Long, dayEnd: Long): Flow<List<Day>> {
        return dayDataBaseRoom.dayDatabaseDao.getSelectedDays(dayStart, dayEnd)
            .map {
                it.map { dayEntity ->
                    dayEntityMapper.mapToDomainModel(dayEntity)
                }
            }
    }

    override suspend fun getCertainDays(dayStart: Long): Flow<List<Day>> {
        return dayDataBaseRoom.dayDatabaseDao.getCertainDays(dayStart)
            .map {
                it.map { dayEntity ->
                    dayEntityMapper.mapToDomainModel(dayEntity)
                }
            }
    }

    override suspend fun getDaysByLimit(limit: Int): Flow<List<Day>> {
        return dayDataBaseRoom.dayDatabaseDao.getDaysByLimit(limit)
            .map {
                it.map { dayEntity ->
                    dayEntityMapper.mapToDomainModel(dayEntity)
                }
            }
    }

    override suspend fun getLastDay(): Flow<Day?> {
        val dayEntity = dayDataBaseRoom.dayDatabaseDao.getLastDay()

        return dayEntity.map {
            it?.let(dayEntityMapper::mapToDomainModel)
        }
    }

    override suspend fun getDayById(id: Long): Flow<Day?> {
        val dayEntity = dayDataBaseRoom.dayDatabaseDao.getDayById(id)
        return dayEntity.map {
            it?.let(dayEntityMapper::mapToDomainModel)
        }
    }

    override suspend fun deleteDay(id: Long) {
        dayDataBaseRoom.dayDatabaseDao.deleteDay(id)
        firebaseRealtimeDB.deleteDay(id)
    }

    override suspend fun deleteAllDaysLocal() {
        dayDataBaseRoom.dayDatabaseDao.clear()
    }

    override suspend fun deleteAllDaysRemote() {
        firebaseRealtimeDB.deleteAllDays()
    }

    override suspend fun insertLocal(day: Day) {
        val dayEntity = dayEntityMapper.mapFromDomainModel(day)
        dayDataBaseRoom.dayDatabaseDao.insert(dayEntity)
    }

    override suspend fun insertRemote(day: Day) {
        firebaseRealtimeDB.insert(day)
    }

    override suspend fun update(day: Day) {
        val dayEntity = dayEntityMapper.mapFromDomainModel(day)
        dayDataBaseRoom.dayDatabaseDao.update(dayEntity)

        firebaseRealtimeDB.update(day)
    }
}

