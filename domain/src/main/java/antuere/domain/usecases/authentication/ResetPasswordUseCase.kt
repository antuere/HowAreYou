package antuere.domain.usecases.authentication

import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.ResetPassResultListener
import antuere.domain.authentication_manager.ResultListener

class ResetPasswordUseCase(private val authenticationManager: AuthenticationManager) : UseCaseForAuth {

    override suspend fun invoke(resultListener: ResultListener, vararg fields: String) {
        authenticationManager.resetPassword(
            email = fields.component1(),
            resetPassResultListener = resultListener as ResetPassResultListener
        )
    }
}