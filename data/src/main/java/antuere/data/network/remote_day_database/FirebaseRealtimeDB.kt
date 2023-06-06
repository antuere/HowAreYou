package antuere.data.network.remote_day_database

import antuere.data.network.NetworkInfo
import antuere.data.network.remote_day_database.entities.DayEntityRemote
import antuere.data.network.remote_day_database.mapping.DayEntityRemoteMapper
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.dto.Day
import antuere.domain.remote_db.RemoteDbApi
import antuere.domain.util.TimeUtility.convertToUTC
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject

class FirebaseRealtimeDB @Inject constructor(
    private val realTimeDb: DatabaseReference,
    private val authManager: AuthenticationManager,
    private val networkInfo: NetworkInfo,
    private val dayEntityMapperRemote: DayEntityRemoteMapper,
) : RemoteDbApi {

    companion object {
        private const val DAYS_PATH = "days"
        private const val USERS_PATH = "users"
        private const val QUOTE_PATH = "quotes"
    }

    private val daysNode: DatabaseReference?
        get() = if (authManager.isHasUser()) realTimeDb.child(USERS_PATH)
            .child(authManager.userId)
            .child(DAYS_PATH) else null


    fun getQuotesNode(): DatabaseReference {
        val userLanguage = Locale.getDefault().language
        val languagePath = if (userLanguage == "ru") "russian" else "others"
        return realTimeDb.child(QUOTE_PATH).child(languagePath)
    }


    override suspend fun getDays(): List<Day> {
        if (!networkInfo.isNetworkAvailable()) return emptyList()
        val daysNode = daysNode ?: return emptyList()
        val query = daysNode.get().await()
        if (!query.exists()) return emptyList()
        return query.children.mapNotNull {
            it.getValue(DayEntityRemote::class.java)?.let(dayEntityMapperRemote::mapToDomainModel)
        }
    }


    override suspend fun deleteDay(id: Long) {
        if (!authManager.isHasUser()) return
        val daysNode = daysNode ?: return
        daysNode.child(id.convertToUTC().toString())
            .setValue(null).await()
    }

    override suspend fun deleteAllDays() {
        if (!authManager.isHasUser()) return
        val query = daysNode?.get()?.await() ?: return
        if (!query.exists()) return
        query.children.forEach {
            it.ref.removeValue()
        }
    }

    override suspend fun insert(day: Day) {
        if (!authManager.isHasUser()) return
        val daysNode = daysNode ?: return
        val dayRemote = dayEntityMapperRemote.mapFromDomainModel(day)
        daysNode.child(dayRemote.dayId.toString())
            .setValue(dayRemote).await()
    }

    override suspend fun insert(days: List<Day>) {
        if (!authManager.isHasUser()) return
        val daysNode = daysNode ?: return
        val daysRemote = days.map {
            dayEntityMapperRemote.mapFromDomainModel(it)
        }
        daysRemote.forEach { dayRemote ->
            daysNode.child(dayRemote.dayId.toString()).setValue(dayRemote).await()
        }
    }


    override suspend fun update(day: Day) {
        if (!authManager.isHasUser()) return
        val daysNode = daysNode ?: return
        val dayRemote = dayEntityMapperRemote.mapFromDomainModel(day)
        daysNode.child(dayRemote.dayId.toString())
            .setValue(dayRemote).await()
    }
}