package antuere.data.local_day_database.mapping

import antuere.data.local_day_database.entities.DayEntity
import antuere.data.util.SmileProvider
import antuere.domain.dto.Day
import antuere.domain.mapping.DomainMapper

class DayEntityMapper : DomainMapper<DayEntity, Day> {

    override fun mapToDomainModel(model: DayEntity): Day {
        val imageId = SmileProvider.getResIdSmileBySmileImage(model.smileImage)

        return Day(
            dayId = model.dayId,
            imageResId = imageId,
            dayText = model.dayText,
            dateString = model.dateString,
            isFavorite = model.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: Day): DayEntity {
        val imageName = SmileProvider.getSmileImageById(domainModel.imageResId)
        return DayEntity(
            dayId = domainModel.dayId,
            smileImage = imageName,
            dayText = domainModel.dayText,
            dateString = domainModel.dateString,
            isFavorite = domainModel.isFavorite
        )
    }
}