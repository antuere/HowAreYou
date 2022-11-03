package antuere.data.remote_day_database.mapping

import antuere.data.remote_day_database.entities.DayEntityRemote
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper


class DayEntityRemoteMapper : DomainMapper<DayEntityRemote, Day> {

    override fun mapToDomainModel(model: DayEntityRemote): Day {
        return Day(
            dayId = model.dayId,
            imageName = model.imageName,
            dayText = model.dayText,
            dateString = model.dateString,
            isFavorite = model.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntityRemote {
        return DayEntityRemote(
            dayId = domainModel.dayId,
            imageName = domainModel.imageName,
            dayText = domainModel.dayText,
            dateString = domainModel.dateString,
            isFavorite = domainModel.isFavorite
        )
    }
}