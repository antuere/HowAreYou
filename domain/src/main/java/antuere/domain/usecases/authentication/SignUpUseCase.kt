package antuere.domain.usecases.authentication

import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.authentication_manager.ResultListener

class SignUpUseCase(private val authenticationManager: AuthenticationManager) : UseCaseForAuth {

    override suspend fun invoke(resultListener: ResultListener, vararg fields: String) {
        authenticationManager.startRegister(
            email = fields.component1(),
            password = fields.component2(),
            name = fields.component3(),
            registerResultListener = resultListener as RegisterResultListener
        )
    }
}

