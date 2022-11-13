package antuere.data.remote_day_database

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

data class FirebaseApi(
    val auth: FirebaseAuth,
    private val realTimeDb: DatabaseReference,
    private val googleSignInClient: GoogleSignInClient,
    private val context: Context
) {

    companion object {
        private const val DAYS_PATH = "days"
        private const val USERS_PATH = "users"
        private const val QUOTE_PATH = "quotes"
    }

    fun isNetworkAvailable(): Boolean {
        val connectionManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectionManager.getNetworkCapabilities(connectionManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }

    fun isHasUser(): Boolean {
        return auth.currentUser != null
    }

    fun getDaysNode(): DatabaseReference? {
        return if (isHasUser()) realTimeDb.child(USERS_PATH).child(auth.currentUser!!.uid)
            .child(DAYS_PATH) else null
    }

    private fun getUserNode(): DatabaseReference? {
        return if (isHasUser()) realTimeDb.child(USERS_PATH).child(auth.currentUser!!.uid)
        else null
    }

    fun getQuotesNode(): DatabaseReference {
        return realTimeDb.child(QUOTE_PATH)
    }

    fun setUserNickname(name: String) {
        getUserNode()?.child("nickName")?.setValue(name)
    }

    suspend fun getUserNickname(): String? {
        return if (isHasUser() && isNetworkAvailable()) {
            val query = getUserNode()!!.child("nickName").get().await()
            if (query.exists()) {
                query.getValue(String::class.java)
            } else {
                null
            }
        } else null
    }

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

}