package antuere.domain.usecases

import antuere.domain.repository.QuoteRepository

class UpdDayQuoteByRemoteUseCase(private val quoteRepository: QuoteRepository) :
    UseCase<Boolean, Unit> {

    override suspend fun invoke(param: Unit): Boolean {
        return quoteRepository.updateQuoteRemote()
    }
}