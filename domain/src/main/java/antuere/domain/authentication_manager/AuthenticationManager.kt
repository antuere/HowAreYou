package antuere.domain.authentication_manager

import antuere.domain.dto.AuthMethod
import antuere.domain.dto.AuthProvider

interface AuthenticationManager {

    val userId: String

    fun startAuth(
        email: String,
        password: String,
        loginResultListener: LoginResultListener,
    )

    fun startRegister(
        email: String,
        password: String,
        name: String,
        registerResultListener: RegisterResultListener,
    )

    fun startAuthByGoogle(
        accIdToken: String?,
        name: String,
        registerResultListener: RegisterResultListener,
    )

    fun getAuthMethod(): AuthMethod

    fun reAuthenticate(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        authProvider: AuthProvider,
    )

    fun signOut()

    fun isHasUser(): Boolean

    fun setUserNicknameOnServer(name: String)

    fun resetPassword(email: String, resetPassResultListener: ResetPassResultListener)

    suspend fun deleteAccount(onSuccess: () -> Unit, onFailure: (String?) -> Unit)

    suspend fun isHasThisAccountOnServer(): Boolean

    suspend fun getUserNickName(): String?

    suspend fun getUserEmail(): String?

}