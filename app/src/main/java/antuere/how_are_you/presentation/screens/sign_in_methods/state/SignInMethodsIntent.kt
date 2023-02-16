package antuere.how_are_you.presentation.screens.sign_in_methods.state

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

sealed interface SignInMethodsIntent {
    object GoogleMethodClicked : SignInMethodsIntent
    object EmailMethodClicked : SignInMethodsIntent
    data class GoogleAccAdded(val task: Task<GoogleSignInAccount>) : SignInMethodsIntent
}