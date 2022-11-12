package antuere.domain.usecases

import antuere.domain.repository.SettingsRepository

class SaveUserNicknameUseCase(private val settingsRepository: SettingsRepository) :
    UseCase<Unit, String> {

    override suspend fun invoke(param: String) {
        settingsRepository.saveUserNickname(param)
    }
}