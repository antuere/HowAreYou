package antuere.domain.dto

sealed class AuthProvider {
    data class Email(val password: String) : AuthProvider()
    data class GoogleAccount(val accIdToken: String) : AuthProvider()
}