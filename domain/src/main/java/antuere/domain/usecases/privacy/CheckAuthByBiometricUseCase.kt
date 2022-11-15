package antuere.domain.usecases.privacy

import antuere.domain.privacy_manager.PrivacyManager
import antuere.domain.usecases.UseCaseDefault

class CheckAuthByBiometricUseCase(private val privacyManager: PrivacyManager) :
    UseCaseDefault<Boolean, Unit> {

    override suspend fun invoke(param: Unit): Boolean {
        return privacyManager.isUserAuthByBiometric
    }
}