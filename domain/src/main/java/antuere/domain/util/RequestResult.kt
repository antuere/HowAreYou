package antuere.domain.util


sealed class RequestResult {
    object Success : RequestResult()
    data class Failure(val message: String?) : RequestResult()
}
