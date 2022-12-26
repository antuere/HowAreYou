package antuere.domain.usecases.authentication

import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.authentication_manager.ResultListener

class SignInByGoogleUseCase(private val authenticationManager: AuthenticationManager) :
    UseCaseForAuth {

    override suspend fun invoke(resultListener: ResultListener, vararg fields: String) {
        authenticationManager.startAuthByGoogle(
            accIdToken = fields.component1(),
            name = fields.component2(),
            registerResultListener = resultListener as RegisterResultListener
        )
    }
}