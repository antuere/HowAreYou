package antuere.domain.usecases.user_settings

import antuere.domain.repository.SettingsRepository
import antuere.domain.usecases.UseCaseDefault
import kotlinx.coroutines.flow.Flow

class GetUserNicknameUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Flow<String>, Unit> {

    override suspend fun invoke(param: Unit): Flow<String> {
        return settingsRepository.getUserNickname()
    }
}