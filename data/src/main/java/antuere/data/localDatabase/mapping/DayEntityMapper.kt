package antuere.data.localDatabase.mapping

import antuere.data.localDatabase.entities.DayEntity
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper

class DayEntityMapper : DomainMapper<DayEntity, Day> {

    override fun mapToDomainModel(model: DayEntity): Day {
        return Day(
            dayId = model.dayId,
            currentDate = model.currentDate,
            imageId = model.imageId,
            dayText = model.dayText,
            currentDateString = model.currentDateString,
            isFavorite = model.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntity {
        return DayEntity(
            dayId = domainModel.dayId,
            currentDate = domainModel.currentDate,
            imageId = domainModel.imageId,
            dayText = domainModel.dayText,
            currentDateString = domainModel.currentDateString,
            isFavorite = domainModel.isFavorite
        )
    }
}