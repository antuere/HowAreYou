package antuere.domain.usecases

import antuere.domain.dto.Settings
import antuere.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(private val settingsRepository: SettingsRepository) :
    UseCase<Flow<Settings>, Unit> {

    override suspend fun invoke(param: Unit): Flow<Settings> {
        return settingsRepository.getSettings()
    }
}