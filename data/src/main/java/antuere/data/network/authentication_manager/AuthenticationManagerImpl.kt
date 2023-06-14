package antuere.data.network.authentication_manager

import antuere.data.network.NetworkInfo
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.LoginResultListener
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.authentication_manager.ResetPassResultListener
import antuere.domain.dto.AuthMethod
import antuere.domain.dto.AuthProvider
import com.google.firebase.FirebaseException
import com.google.firebase.auth.EmailAuthProvider
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

    override fun getAuthMethod(): AuthMethod {
        val user = firebaseAuth.currentUser ?: return AuthMethod.NOT_AUTH

        user.providerData.forEach {
            if (it.providerId == EmailAuthProvider.PROVIDER_ID) {
                return AuthMethod.EMAIL
            }

            if (it.providerId == GoogleAuthProvider.PROVIDER_ID) {
                return AuthMethod.GOOGLE
            }
        }

        return AuthMethod.NOT_AUTH
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

    override suspend fun deleteAccount(
        onSuccess:() -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        getUserNode()?.setValue(null)?.await()
        val user = firebaseAuth.currentUser ?: return onFailure(null)
        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: task.toString())
                }
            }
            .addOnFailureListener {
                onFailure(it.message ?: it.toString())
            }
            .await()
    }

    override fun reAuthenticate(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        authProvider: AuthProvider,
    ) {
        val user = firebaseAuth.currentUser ?: return
        val credential = when (authProvider) {
            is AuthProvider.Email -> {
                val userEmail = user.email ?: return
                EmailAuthProvider.getCredential(userEmail, authProvider.password)
            }

            is AuthProvider.GoogleAccount -> {
                GoogleAuthProvider.getCredential(authProvider.accIdToken, null)
            }
        }
        try {
            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(task.exception?.message ?: task.toString())
                    }
                }
                .addOnFailureListener {
                    onFailure(it.message ?: it.toString())
                }
        } catch (e: FirebaseException) {
            onFailure(e.message ?: e.toString())
        }
    }
}