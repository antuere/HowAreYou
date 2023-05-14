package antuere.data.network.authentication_manager

import antuere.data.network.NetworkInfo
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.LoginResultListener
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.authentication_manager.ResetPassResultListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationManagerImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val realTimeDb: DatabaseReference,
    private val networkInfo: NetworkInfo,
) : AuthenticationManager {

    companion object {
        private const val USERS_PATH = "users"
    }

    override val userId: String
        get() {
            return firebaseAuth.currentUser!!.uid
        }

    override fun isHasUser(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun setUserNicknameOnServer(name: String) {
        getUserNode()?.child("nickName")?.setValue(name)
    }

    override fun resetPassword(email: String, resetPassResultListener: ResetPassResultListener) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { resetTask ->
                if (resetTask.isSuccessful) {
                    resetPassResultListener.resetSuccess()
                }
            }
            .addOnFailureListener {
                resetPassResultListener.resetError(it.message!!)
            }
    }

    override suspend fun isHasThisAccountOnServer(): Boolean {
        return getUserNickName() != null
    }

    override suspend fun getUserNickName(): String? {
        return if (isHasUser() && networkInfo.isNetworkAvailable()) {
            val query = getUserNode()!!.child("nickName").get().await()
            if (query.exists()) {
                query.getValue(String::class.java)
            } else {
                null
            }
        } else null
    }

    private fun getUserNode(): DatabaseReference? {
        return if (isHasUser()) realTimeDb.child(USERS_PATH).child(firebaseAuth.currentUser!!.uid)
        else null
    }

    override fun startAuth(
        email: String,
        password: String,
        loginResultListener: LoginResultListener,
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
                    loginResultListener.loginSuccess()
                }
            }.addOnFailureListener {
                loginResultListener.loginFailed(it.message!!)
            }
    }

    override fun startRegister(
        email: String,
        password: String,
        name: String,
        registerResultListener: RegisterResultListener,
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { signUpTask ->
                if (signUpTask.isSuccessful) {
                    registerResultListener.registerSuccess(name)
                }
            }.addOnFailureListener {
                registerResultListener.registerFailed(it.message!!)
            }
    }

    override fun startAuthByGoogle(
        accIdToken: String?,
        name: String,
        registerResultListener: RegisterResultListener,
    ) {
        val credential = GoogleAuthProvider.getCredential(accIdToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    registerResultListener.registerSuccess(name)
                }
            }
            .addOnFailureListener {
                registerResultListener.registerFailed(it.message ?: "Error")
            }
    }


    override fun signOut() {
        firebaseAuth.signOut()
    }
}