package antuere.domain.usecases.privacy

import antuere.domain.privacy_manager.PrivacyManager
import antuere.domain.usecases.UseCaseDefault

class DoneAuthUseCase(private val privacyManager: PrivacyManager) : UseCaseDefault<Unit, Unit> {
    override suspend fun invoke(param: Unit) {
        privacyManager.doneAuthUser()
    }
}