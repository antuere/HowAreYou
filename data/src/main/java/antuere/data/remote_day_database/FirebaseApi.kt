package antuere.data.remote_day_database

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

data class FirebaseApi(
    val auth: FirebaseAuth,
    private val realTimeDb: DatabaseReference,
    private val googleSignInClient: GoogleSignInClient
) {
    companion object {
        private const val DAYS_PATH = "days"
        private const val USERS_PATH = "users"
        private const val QUOTE_PATH = "quotes"
    }

    fun isHasUser(): Boolean {
        return auth.currentUser != null
    }

    fun getDaysNode(): DatabaseReference? {
        return if (isHasUser()) realTimeDb.child(USERS_PATH).child(auth.currentUser!!.uid)
            .child(DAYS_PATH) else null
    }

    fun getUserNode(): DatabaseReference? {
        return if (isHasUser()) realTimeDb.child(USERS_PATH).child(auth.currentUser!!.uid)
        else null
    }

    fun getQuotesNode(): DatabaseReference {
        return realTimeDb.child(QUOTE_PATH)
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

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

}