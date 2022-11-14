package antuere.domain.authentication_manager

interface RegisterResultListener : ResultListener {
    fun registerSuccess(name : String)
    fun registerFailed(message: String)
}