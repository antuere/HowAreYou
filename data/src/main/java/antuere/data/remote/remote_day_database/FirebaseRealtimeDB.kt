package antuere.data.remote.remote_day_database

import antuere.data.remote.NetworkInfo
import antuere.data.remote.remote_day_database.entities.DayEntityRemote
import antuere.data.remote.remote_day_database.mapping.DayEntityRemoteMapper
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.dto.Day
import antuere.domain.remote_db.RemoteDbApi
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class FirebaseRealtimeDB @Inject constructor(
    private val realTimeDb: DatabaseReference,
    private val authManager: AuthenticationManager,
    private val networkInfo: NetworkInfo,
    private val dayEntityMapperRemote: DayEntityRemoteMapper
) : RemoteDbApi {

    companion object {
        private const val DAYS_PATH = "days"
        private const val USERS_PATH = "users"
        private const val QUOTE_PATH = "quotes"
    }

    private var daysNode: DatabaseReference? = null

    private fun getDaysNode(): DatabaseReference? {
        return if (authManager.isHasUser()) realTimeDb.child(USERS_PATH)
            .child(authManager.userId)
            .child(DAYS_PATH) else null
    }


    fun getQuotesNode(): DatabaseReference {
        return realTimeDb.child(QUOTE_PATH)
    }

    override suspend fun getDays(): List<Day> {
        val result = arrayListOf<Day>()
        daysNode = getDaysNode()
        if (daysNode != null && networkInfo.isNetworkAvailable()) {
            val query = daysNode!!.get().await()
            delay(100)
            if (query.exists()) {
                query.children.forEach {
                    val dayRemote = it.getValue(DayEntityRemote::class.java)
                    if (dayRemote != null) {
                        val day = dayEntityMapperRemote.mapToDomainModel(dayRemote)
                        result.add(day)
                    }
                }
            }
        }
        return result.toList()

    }

    override suspend fun deleteDay(id: Long) {
        if (authManager.isHasUser()) {
            val query = daysNode
                ?.orderByChild("dayId")
                ?.equalTo(id.toDouble())
                ?.get()
                ?.await()

            query?.children?.forEach {
                it.ref.removeValue()
            }
        }
    }

    override suspend fun deleteAllDays() {
        if (authManager.isHasUser()) {
            val query = daysNode?.get()?.await()
            if (query?.exists() == true) {
                query.children.forEach {
                    it.ref.removeValue()
                }
            }
        }
    }

    override suspend fun insert(day: Day) {
        if (authManager.isHasUser()) {
            val dayRemote = dayEntityMapperRemote.mapFromDomainModel(day)
            daysNode?.child(dayRemote.dayId.toString())?.setValue(dayRemote)?.await()
        }
    }


    override suspend fun update(day: Day) {
        if(authManager.isHasUser()){
            val dayRemote = dayEntityMapperRemote.mapFromDomainModel(day)
            daysNode?.child(dayRemote.dayId.toString())?.setValue(dayRemote)?.await()
        }
    }

}