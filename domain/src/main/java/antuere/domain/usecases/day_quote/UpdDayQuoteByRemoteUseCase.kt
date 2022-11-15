package antuere.domain.usecases.day_quote

import antuere.domain.repository.QuoteRepository
import antuere.domain.usecases.UseCaseDefault

class UpdDayQuoteByRemoteUseCase(private val quoteRepository: QuoteRepository) :
    UseCaseDefault<Boolean, Unit> {

    override suspend fun invoke(param: Unit): Boolean {
        return quoteRepository.updateQuoteRemote()
    }
}