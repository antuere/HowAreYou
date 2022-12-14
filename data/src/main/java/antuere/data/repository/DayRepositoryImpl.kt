package antuere.data.repository

import antuere.data.local_day_database.DayDatabase
import antuere.data.local_day_database.mapping.DayEntityMapper
import antuere.data.remote.remote_day_database.FirebaseRealtimeDB
import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
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
                Timber.i("days not null")
                daysFromServer.forEach { day ->
                    insertLocal(day)
                }

                getAllDays().collect { days ->
                    days.forEach { insertRemote(it) }
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

    override suspend fun getSelectedDays(dayStart: Long, dayEnd: Long): Flow<List<Day>> =
        withContext(Dispatchers.IO) {
            dayDataBaseRoom.dayDatabaseDao.getSelectedDays(dayStart, dayEnd)
                .map {
                    it.map { dayEntity ->
                        dayEntityMapper.mapToDomainModel(dayEntity)
                    }
                }
        }

    override suspend fun getCertainDays(dayStart: Long): Flow<List<Day>> =
        withContext(Dispatchers.IO) {
            dayDataBaseRoom.dayDatabaseDao.getCertainDays(dayStart)
                .map {
                    it.map { dayEntity ->
                        dayEntityMapper.mapToDomainModel(dayEntity)
                    }
                }
        }

    override suspend fun getDaysByLimit(limit: Int): Flow<List<Day>> = withContext(Dispatchers.IO) {
        dayDataBaseRoom.dayDatabaseDao.getDaysByLimit(limit)
            .map {
                it.map { dayEntity ->
                    dayEntityMapper.mapToDomainModel(dayEntity)
                }
            }
    }

    override suspend fun getDay(): Day? = withContext(Dispatchers.IO) {
        val dayEntity = dayDataBaseRoom.dayDatabaseDao.getDay()
        dayEntity?.let(dayEntityMapper::mapToDomainModel)
    }

    override suspend fun getDayById(id: Long): Day? = withContext(Dispatchers.IO) {
        val dayEntity = dayDataBaseRoom.dayDatabaseDao.getDayById(id)
        dayEntity?.let(dayEntityMapper::mapToDomainModel)
    }

    override suspend fun deleteDay(id: Long): Unit = withContext(Dispatchers.IO) {
        dayDataBaseRoom.dayDatabaseDao.deleteDay(id)
        firebaseRealtimeDB.deleteDay(id)
    }

    override suspend fun deleteAllDaysLocal() = withContext(Dispatchers.IO) {
        dayDataBaseRoom.dayDatabaseDao.clear()
    }

    override suspend fun deleteAllDaysRemote() = withContext(Dispatchers.IO) {
        firebaseRealtimeDB.deleteAllDays()
    }

    override suspend fun insertLocal(day: Day) = withContext(Dispatchers.IO) {
        val dayEntity = dayEntityMapper.mapFromDomainModel(day)
        dayDataBaseRoom.dayDatabaseDao.insert(dayEntity)
    }

    override suspend fun insertRemote(day: Day) {
        firebaseRealtimeDB.insert(day)
    }

    override suspend fun update(day: Day): Unit = withContext(Dispatchers.IO) {
        val dayEntity = dayEntityMapper.mapFromDomainModel(day)
        dayDataBaseRoom.dayDatabaseDao.update(dayEntity)

        firebaseRealtimeDB.update(day)
    }
}

