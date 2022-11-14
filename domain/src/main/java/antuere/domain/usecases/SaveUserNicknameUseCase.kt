package antuere.domain.usecases

import antuere.domain.repository.SettingsRepository

class SaveUserNicknameUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Unit, String> {

    override suspend fun invoke(param: String) {
        settingsRepository.saveUserNickname(param)
    }
}