package antuere.domain.usecases.user_settings

import antuere.domain.dto.helplines.SupportedCountry
import antuere.domain.repository.SettingsRepository
import antuere.domain.usecases.UseCaseDefault

class SaveSelectedCountryIdUseCase(private val settingsRepository: SettingsRepository) :
    UseCaseDefault<Unit, SupportedCountry> {

    override suspend fun invoke(param: SupportedCountry) {
        return settingsRepository.saveSelectedCountryId(param)
    }
}