package antuere.domain.authentication_manager

interface LoginResultListener : ResultListener {
    fun loginSuccess()
    fun loginFailed(message: String)
}