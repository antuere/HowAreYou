package antuere.domain.usecases.user_settings

import antuere.domain.dto.Settings
import antuere.domain.repository.SettingsRepository
import antuere.domain.usecases.UseCaseDefault
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Flow<Settings>, Unit> {

    override suspend fun invoke(param: Unit): Flow<Settings> {
        return settingsRepository.getSettings()
    }
}