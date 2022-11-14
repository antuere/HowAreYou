package antuere.domain.usecases.authentication

import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.usecases.UseCaseDefault

class SignOutUseCase (private val authenticationManager: AuthenticationManager) : UseCaseDefault<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        authenticationManager.signOut()
    }

}
