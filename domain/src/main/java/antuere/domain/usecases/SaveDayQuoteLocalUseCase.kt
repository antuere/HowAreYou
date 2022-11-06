package antuere.domain.usecases

import antuere.domain.dto.Quote
import antuere.domain.repository.QuoteRepository

//TODO удалить если не будет использоваться
class SaveDayQuoteLocalUseCase(private val quoteRepository: QuoteRepository) :
    UseCase<Unit, Quote> {

    override suspend fun invoke(param: Quote) {
        quoteRepository.saveDayQuoteLocal(param)
    }
}
