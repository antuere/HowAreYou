package antuere.data.local_day_database.mapping

import antuere.data.local_day_database.entities.DayEntity
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper

class DayEntityMapper : DomainMapper<DayEntity, Day> {

    override fun mapToDomainModel(model: DayEntity): Day {
        return Day(
            dayId = model.dayId,
            imageId = model.imageId,
            dayText = model.dayText,
            dateString = model.dateString,
            isFavorite = model.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntity {
        return DayEntity(
            dayId = domainModel.dayId,
            imageId = domainModel.imageId,
            dayText = domainModel.dayText,
            dateString = domainModel.dateString,
            isFavorite = domainModel.isFavorite
        )
    }
}