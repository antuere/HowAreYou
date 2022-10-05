package antuere.data.localDatabase.mappers

import antuere.data.localDatabase.DayEntity
import antuere.domain.Day
import antuere.domain.util.DomainMapper

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