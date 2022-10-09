package antuere.data.room.mapping

import antuere.data.room.entities.DayEntity
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper

class DayEntityMapper : DomainMapper<DayEntity, Day> {

    override fun mapToDomainModel(model: DayEntity): Day {
        return Day(
            dayId = model.dayId,
            currentDate = model.currentDate,
            imageId = model.imageId,
            dayText = model.dayText,
            currentDateString = model.currentDateString
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntity {
        return DayEntity(
            dayId = domainModel.dayId,
            currentDate = domainModel.currentDate,
            imageId = domainModel.imageId,
            dayText = domainModel.dayText,
            currentDateString = domainModel.currentDateString
        )
    }
}