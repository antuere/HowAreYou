package antuere.domain.usecases.authentication

import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.usecases.UseCaseDefault

class SetUserNicknameOnServerUseCase(private val authenticationManager: AuthenticationManager) :
    UseCaseDefault<Unit, String> {

    override suspend fun invoke(param: String) {
        authenticationManager.setUserNickname(param)
    }
}