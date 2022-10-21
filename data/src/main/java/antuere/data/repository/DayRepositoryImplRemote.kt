package antuere.data.repository

import antuere.data.remoteDataBase.entities.DayEntityRemote
import antuere.data.remoteDataBase.mapping.DayEntityMapperRemote
import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import antuere.domain.util.TimeUtility
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class DayRepositoryImplRemote @Inject constructor(
    dayDatabaseRemote: DatabaseReference,
    private val dayEntityMapperRemote: DayEntityMapperRemote
) : DayRepository {

    companion object {
        private const val DAYS_PATH = "Days"
    }

    private val daysNode = dayDatabaseRemote.child(DAYS_PATH)

    override suspend fun getAllDays(): Flow<List<Day>> {
        val query = daysNode.get().await()
        val days = mutableListOf<Day>()

        if (query.exists()) {
            query.children.forEach {
                val dayRemote = it.getValue(DayEntityRemote::class.java)
                val day = dayEntityMapperRemote.mapToDomainModel(dayRemote!!)
                days.add(day)
            }
        }
        Timber.e("error from FireBase get all days: $days")

        return flow {
            emit(days)
        }
    }


    override suspend fun getFavoritesDays(): Flow<List<Day>> {
        val query = daysNode
            .orderByChild("isFavorite")
            .equalTo(true)
            .get()
            .await()

        val days = mutableListOf<Day>()
        if (query.exists()) {
            query.children.forEach {
                val dayRemote = it.getValue(DayEntityRemote::class.java)
                val day = dayEntityMapperRemote.mapToDomainModel(dayRemote!!)
                days.add(day)
            }
        }
        return flow {
            emit(days)
        }
    }

    override suspend fun getDay(): Day? {
        val query = daysNode
            .limitToLast(1)
            .get()
            .await()

        var result: Day? = null

        if (query.exists()) {
            val dayRemote = query.children.first().getValue(DayEntityRemote::class.java)
            dayRemote.let {
                val day = dayEntityMapperRemote.mapToDomainModel(dayRemote!!)
                Timber.e("error day in dayRemote is: $day")
                result = day
            }
        }

        return result
    }

    override suspend fun getDayById(id: Long): Day? {
        val query = daysNode
            .orderByChild("dayId")
            .equalTo(id.toDouble())
            .get()
            .await()

        val dayRemote = query.children.first().getValue(DayEntityRemote::class.java)

        return dayEntityMapperRemote.mapToDomainModel(dayRemote!!)
    }

    override suspend fun deleteDay(id: Long) {
        val query = daysNode
            .orderByChild("dayId")
            .equalTo(id.toDouble())
            .get()
            .await()

        query.children.forEach {
            it.ref.removeValue()
        }

    }

    override suspend fun deleteAllDays() {
        val dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    it.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e("error from FireBase delete all days: ${error.message}")
            }
        }
        daysNode.addListenerForSingleValueEvent(dataListener)
    }

    override suspend fun insert(day: Day) {
        val dayRemote = dayEntityMapperRemote.mapFromDomainModel(day)
        val key = TimeUtility.currentDate.time
        dayRemote.dayId = key
        daysNode.child(key.toString()).setValue(dayRemote).await()
    }

    override suspend fun update(day: Day) {
        val dayRemote = dayEntityMapperRemote.mapFromDomainModel(day)
        daysNode.child(dayRemote.dayId.toString()).setValue(dayRemote).await()
    }

    override fun updateDaysFromFireBase() {
        TODO("Not yet implemented")
    }
}