package antuere.domain.usecases

import antuere.domain.dto.Quote
import antuere.domain.repository.QuoteRepository

class GetDayQuoteLocalUseCase(private val quoteRepository: QuoteRepository) :
    UseCaseDefault<Quote?, Unit> {

    override suspend fun invoke(param: Unit): Quote {
        return quoteRepository.getDayQuoteLocal()
    }
}