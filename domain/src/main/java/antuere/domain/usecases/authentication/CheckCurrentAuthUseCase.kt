package antuere.domain.usecases.authentication

import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.usecases.UseCaseDefault

class CheckCurrentAuthUseCase(private val authenticationManager: AuthenticationManager) :
    UseCaseDefault<Boolean, Unit> {

    override suspend fun invoke(param: Unit): Boolean {
        return authenticationManager.isHasUser()
    }
}