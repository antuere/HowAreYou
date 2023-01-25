package antuere.how_are_you.presentation.sign_in_methods

sealed class SignInMethodsState {

    object UserAuthorized : SignInMethodsState()

    data class Error(var message: String) : SignInMethodsState()
}

