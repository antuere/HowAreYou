package antuere.data.remoteDataBase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

data class FirebaseApi(
    val auth: FirebaseAuth,
    private val realTimeDb: DatabaseReference
) {
    companion object {
        private const val DAYS_PATH = "days"
    }

    private fun isHasUser(): Boolean {
        return auth.currentUser != null
    }

    fun getDaysNode(): DatabaseReference? {
        return if (isHasUser()) realTimeDb.child(auth.currentUser!!.uid)
            .child(DAYS_PATH) else null
    }

    fun getUserNode(): DatabaseReference? {
        return if (isHasUser()) realTimeDb.child(auth.currentUser!!.uid)
        else null
    }

     suspend fun getUserNickNameAsync(): Deferred<String?> {
        val scope = CoroutineScope(Dispatchers.IO)
        return scope.async {
            if (isHasUser()) {
                val query = getUserNode()!!.child("nickName").get().await()
                if (query.exists()) {
                    query.getValue(String::class.java)
                } else {
                    null
                }
            } else null
        }
    }

}