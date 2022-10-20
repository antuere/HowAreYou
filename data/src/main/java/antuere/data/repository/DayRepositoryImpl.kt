package antuere.data.repository

import antuere.data.localDatabase.DayDatabase
import antuere.data.localDatabase.mapping.DayEntityMapper
import antuere.data.remoteDataBase.entities.DayEntityRemote
import antuere.data.remoteDataBase.mapping.DayEntityMapperRemote
import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DayRepositoryImpl @Inject constructor(
    private val dayDataBaseRoom: DayDatabase,
    dayDatabaseRemote: DatabaseReference,
    private val dayEntityMapperRemote: DayEntityMapperRemote,
    private val dayEntityMapper: DayEntityMapper,
    firebaseAuth: FirebaseAuth
) : DayRepository {

    companion object {
        private const val DAYS_PATH = "Days"
    }

    private val daysNode = dayDatabaseRemote.child(firebaseAuth.currentUser!!.uid).child(DAYS_PATH)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val query = daysNode.get().await()

            if (query.exists()) {
                query.children.forEach {
                    val dayRemote = it.getValue(DayEntityRemote::class.java)
                    val day = dayEntityMapperRemote.mapToDomainModel(dayRemote!!)
                    insertRoom(day)
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

    override suspend fun getDay(): Day? = withContext(Dispatchers.IO) {
        val dayEntity = dayDataBaseRoom.dayDatabaseDao.getDay()
        dayEntity?.let(dayEntityMapper::mapToDomainModel)
    }

    override suspend fun getDayById(id: Long): Day? = withContext(Dispatchers.IO) {
        val dayEntity = dayDataBaseRoom.dayDatabaseDao.getDayById(id)
        dayEntity?.let(dayEntityMapper::mapToDomainModel)
    }

    override suspend fun deleteDay(id: Long) = withContext(Dispatchers.IO) {
        dayDataBaseRoom.dayDatabaseDao.deleteDay(id)

        val query = daysNode
            .orderByChild("dayId")
            .equalTo(id.toDouble())
            .get()
            .await()

        query.children.forEach {
            it.ref.removeValue()
        }
    }

    override suspend fun deleteAllDays() = withContext(Dispatchers.IO) {
        dayDataBaseRoom.dayDatabaseDao.clear()

        val query = daysNode.get().await()
        if (query.exists()) {
            query.children.forEach {
                it.ref.removeValue()
            }
        }
    }

    override suspend fun insert(day: Day) = withContext(Dispatchers.IO) {
        insertRoom(day)
        insertFirebase(day)
    }

    override suspend fun update(day: Day): Unit = withContext(Dispatchers.IO) {
        val dayEntity = dayEntityMapper.mapFromDomainModel(day)
        dayDataBaseRoom.dayDatabaseDao.update(dayEntity)

        val dayRemote = dayEntityMapperRemote.mapFromDomainModel(day)
        daysNode.child(dayRemote.dayId.toString()).setValue(dayRemote).await()
    }


    private suspend fun insertRoom(day: Day) = withContext(Dispatchers.IO) {
        val dayEntity = dayEntityMapper.mapFromDomainModel(day)
        dayDataBaseRoom.dayDatabaseDao.insert(dayEntity)
    }

    private suspend fun insertFirebase(day: Day) {
        val dayRemote = dayEntityMapperRemote.mapFromDomainModel(day)
        daysNode.child(dayRemote.dayId.toString()).setValue(dayRemote).await()
    }
}

