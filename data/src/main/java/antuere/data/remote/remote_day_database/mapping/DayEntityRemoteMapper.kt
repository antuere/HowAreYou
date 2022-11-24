package antuere.data.remote.remote_day_database.mapping

import antuere.data.remote.remote_day_database.entities.DayEntityRemote
import antuere.data.util.SmileProvider
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper


class DayEntityRemoteMapper : DomainMapper<DayEntityRemote, Day> {

    override fun mapToDomainModel(model: DayEntityRemote): Day {
        val imageId = SmileProvider.getSmileStringByString(model.imageName)

        return Day(
            dayId = model.dayId,
            imageResId = imageId,
            dayText = model.dayText,
            dateString = model.dateString,
            isFavorite = model.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntityRemote {
        val imageName = SmileProvider.getSmileImageById(domainModel.imageResId)
        return DayEntityRemote(
            dayId = domainModel.dayId,
            imageName = imageName.name,
            dayText = domainModel.dayText,
            dateString = domainModel.dateString,
            isFavorite = domainModel.isFavorite
        )
    }
}