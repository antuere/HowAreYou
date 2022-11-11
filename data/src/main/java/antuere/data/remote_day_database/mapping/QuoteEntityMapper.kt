package antuere.data.remote_day_database.mapping

import antuere.data.remote_day_database.entities.QuoteEntity
import antuere.domain.dto.Quote
import antuere.domain.mapping.DomainMapper

class QuoteEntityMapper : DomainMapper<QuoteEntity, Quote> {

    override fun mapToDomainModel(model: QuoteEntity): Quote {
        return Quote(model.text, model.author)
    }

    override fun mapFromDomainModel(domainModel: Quote): QuoteEntity {
        return QuoteEntity(domainModel.text, domainModel.author)
    }
}