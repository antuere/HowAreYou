package antuere.how_are_you.presentation.screens.sign_in_methods.state

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import antuere.how_are_you.R

data class SignInMethodsState(
    val emailMethod: SignInMethod = SignInMethod(
        nameId = R.string.login_email,
        iconId = R.drawable.ic_email,
    ),
    val googleMethod: SignInMethod = SignInMethod(
        nameId = R.string.login_google,
        iconId = R.drawable.ic_google,
    ),
)

data class SignInMethod(
    @StringRes val nameId: Int,
    @DrawableRes val iconId: Int,
)