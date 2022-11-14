package antuere.domain.authentication_manager

interface ResetPassResultListener : ResultListener {
    fun resetSuccess()
    fun resetError(message: String)
}