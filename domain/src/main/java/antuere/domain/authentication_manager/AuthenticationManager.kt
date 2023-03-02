package antuere.domain.authentication_manager

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

    fun signOut()

    fun isHasUser(): Boolean

    fun setUserNicknameOnServer(name: String)

    fun resetPassword(email: String, resetPassResultListener: ResetPassResultListener)

    suspend fun isHasThisAccountOnServer(): Boolean

    suspend fun getUserNickName(): String?

}