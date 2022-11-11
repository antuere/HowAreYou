package antuere.data.preferences_data_store.settings_data_store.mapping

import antuere.data.preferences_data_store.settings_data_store.entities.SettingsEntity
import antuere.domain.dto.Settings
import antuere.domain.mapping.DomainMapper

class SettingsEntityMapper : DomainMapper<SettingsEntity, Settings> {

    override fun mapToDomainModel(model: SettingsEntity): Settings {
        return Settings(
            model.isBiometricEnabled,
            model.isPinCodeEnabled,
            model.isShowWorriedDialog
        )
    }

    override fun mapFromDomainModel(domainModel: Settings): SettingsEntity {
        return SettingsEntity(
            domainModel.isBiometricEnabled,
            domainModel.isPinCodeEnabled,
            domainModel.isShowWorriedDialog
        )
    }
}