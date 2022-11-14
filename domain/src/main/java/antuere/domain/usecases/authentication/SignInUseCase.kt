package antuere.domain.usecases.authentication

import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.LoginResultListener
import antuere.domain.authentication_manager.ResultListener

class SignInUseCase(private val authenticationManager: AuthenticationManager) : UseCaseForAuth {

    override suspend fun invoke(resultListener: ResultListener, vararg fields: String) {
        authenticationManager.startAuth(
            email = fields.component1(),
            password = fields.component2(),
            loginResultListener = resultListener as LoginResultListener
        )
    }
}