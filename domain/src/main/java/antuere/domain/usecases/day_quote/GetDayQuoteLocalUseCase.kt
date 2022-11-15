package antuere.domain.usecases.day_quote

import antuere.domain.dto.Quote
import antuere.domain.repository.QuoteRepository
import antuere.domain.usecases.UseCaseDefault

class GetDayQuoteLocalUseCase(private val quoteRepository: QuoteRepository) :
    UseCaseDefault<Quote?, Unit> {

    override suspend fun invoke(param: Unit): Quote {
        return quoteRepository.getDayQuoteLocal()
    }
}