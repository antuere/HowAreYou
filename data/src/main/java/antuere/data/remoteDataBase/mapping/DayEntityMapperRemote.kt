package antuere.data.remoteDataBase.mapping

import antuere.data.remoteDataBase.converters.ConvertersRemote
import antuere.data.remoteDataBase.entities.DayEntityRemote
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper


class DayEntityMapperRemote : DomainMapper<DayEntityRemote, Day> {

    override fun mapToDomainModel(model: DayEntityRemote): Day {
        return Day(
            dayId = model.dayId,
            currentDate = ConvertersRemote.calendarFromLong(model.currentDate)!!,
            imageId = model.imageId,
            dayText = model.dayText,
            currentDateString = model.currentDateString,
            isFavorite = model.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntityRemote {
        return DayEntityRemote(
            dayId = domainModel.dayId,
            currentDate = ConvertersRemote.calendarToLong(domainModel.currentDate)!!,
            imageId = domainModel.imageId,
            dayText = domainModel.dayText,
            currentDateString = domainModel.currentDateString,
            isFavorite = domainModel.isFavorite
        )
    }
}