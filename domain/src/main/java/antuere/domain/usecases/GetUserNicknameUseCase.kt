package antuere.domain.usecases

import antuere.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetUserNicknameUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Flow<String>, Unit> {

    override suspend fun invoke(param: Unit): Flow<String> {
        return settingsRepository.getUserNickname()
    }
}