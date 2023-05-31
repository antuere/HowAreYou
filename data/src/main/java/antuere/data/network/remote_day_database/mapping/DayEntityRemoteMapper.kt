package antuere.data.network.remote_day_database.mapping

import antuere.data.network.remote_day_database.entities.DayEntityRemote
import antuere.data.util.SmileProvider
import antuere.data.util.decodeByBase64
import antuere.data.util.encodeByBase64
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper
import antuere.domain.util.TimeUtility.convertFromUTC
import antuere.domain.util.TimeUtility.convertToUTC


class DayEntityRemoteMapper : DomainMapper<DayEntityRemote, Day> {

    override fun mapToDomainModel(model: DayEntityRemote): Day {
        val imageId = SmileProvider.getSmileStringByString(model.imageName)
        return Day(
            dayId = model.dayId.convertFromUTC(),
            imageResId = imageId,
            dayText = if (model.dayId > 1678406400000) model.dayText.decodeByBase64() else model.dayText,
            dateString = model.dateString,
            isFavorite = model.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntityRemote {
        val imageName = SmileProvider.getSmileImageById(domainModel.imageResId)
        return DayEntityRemote(
            dayId = domainModel.dayId.convertToUTC(),
            imageName = imageName.name,
            dayText = if (domainModel.dayId > 1678406400000) domainModel.dayText.encodeByBase64() else domainModel.dayText,
            dateString = domainModel.dateString,
            isFavorite = domainModel.isFavorite
        )
    }
}