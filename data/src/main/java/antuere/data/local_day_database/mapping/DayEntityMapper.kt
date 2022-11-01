package antuere.data.local_day_database.mapping

import antuere.data.local_day_database.entities.DayEntity
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper

class DayEntityMapper : DomainMapper<DayEntity, Day> {

    override fun mapToDomainModel(model: DayEntity): Day {
        return Day(
            dayId = model.dayId,
            imageName = model.imageName,
            dayText = model.dayText,
            dateString = model.dateString,
            isFavorite = model.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntity {
        return DayEntity(
            dayId = domainModel.dayId,
            imageName = domainModel.imageName,
            dayText = domainModel.dayText,
            dateString = domainModel.dateString,
            isFavorite = domainModel.isFavorite
        )
    }
}