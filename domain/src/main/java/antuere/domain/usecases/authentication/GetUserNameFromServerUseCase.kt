package antuere.domain.usecases.authentication

import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.usecases.UseCaseDefault

class GetUserNameFromServerUseCase(private val authenticationManager: AuthenticationManager) :
    UseCaseDefault<String?, Unit> {

    override suspend fun invoke(param: Unit): String? {
        return authenticationManager.getUserNickName()
    }
}