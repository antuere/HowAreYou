package antuere.data.remoteDataBase.mapping

import antuere.data.remoteDataBase.entities.DayEntityRemote
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper


class DayEntityMapperRemote : DomainMapper<DayEntityRemote, Day> {

    override fun mapToDomainModel(model: DayEntityRemote): Day {
        return Day(
            dayId = model.dayId,
            imageId = model.imageId,
            dayText = model.dayText,
            dateString = model.dateString,
            isFavorite = model.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntityRemote {
        return DayEntityRemote(
            dayId = domainModel.dayId,
            imageId = domainModel.imageId,
            dayText = domainModel.dayText,
            dateString = domainModel.dateString,
            isFavorite = domainModel.isFavorite
        )
    }
}