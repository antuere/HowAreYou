package antuere.domain.usecases.user_settings

import antuere.domain.repository.SettingsRepository
import antuere.domain.usecases.UseCaseDefault
import kotlinx.coroutines.flow.Flow

class GetSelectedCountryIdUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Flow<Int>, Unit> {

    override suspend fun invoke(param: Unit): Flow<Int> {
        return settingsRepository.getSelectedCountryId()
    }
}