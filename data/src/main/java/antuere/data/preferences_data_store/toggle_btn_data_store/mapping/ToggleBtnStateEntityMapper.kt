package antuere.data.preferences_data_store.toggle_btn_data_store.mapping

import antuere.data.preferences_data_store.toggle_btn_data_store.entities.ToggleBtnStateEntity
import antuere.domain.dto.ToggleBtnState
import antuere.domain.mapping.DomainMapper

class ToggleBtnStateEntityMapper :
    DomainMapper<ToggleBtnStateEntity, ToggleBtnState> {

    override fun mapToDomainModel(model: ToggleBtnStateEntity): ToggleBtnState {
        return when (model) {
            is ToggleBtnStateEntity.AllDays -> ToggleBtnState.AllDays(model.id)
            is ToggleBtnStateEntity.CurrentMonth -> ToggleBtnState.CurrentMonth(
                model.id
            )
            is ToggleBtnStateEntity.LastWeek -> ToggleBtnState.LastWeek(model.id)
        }
    }

    override fun mapFromDomainModel(domainModel: ToggleBtnState): ToggleBtnStateEntity {
        return when (domainModel) {
            is ToggleBtnState.AllDays -> ToggleBtnStateEntity.AllDays(domainModel.id)
            is ToggleBtnState.CurrentMonth -> ToggleBtnStateEntity.CurrentMonth(
                domainModel.id
            )
            is ToggleBtnState.LastWeek -> ToggleBtnStateEntity.LastWeek(
                domainModel.id
            )
        }
    }
}