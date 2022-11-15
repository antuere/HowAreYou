package antuere.domain.usecases.day_quote

import antuere.domain.dto.Quote
import antuere.domain.repository.QuoteRepository
import antuere.domain.usecases.UseCaseDefault

//TODO удалить если не будет использоваться
class SaveDayQuoteLocalUseCase(private val quoteRepository: QuoteRepository) :
    UseCaseDefault<Unit, Quote> {

    override suspend fun invoke(param: Quote) {
        quoteRepository.saveDayQuoteLocal(param)
    }
}
