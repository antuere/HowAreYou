package antuere.domain.repository

import antuere.domain.dto.Quote

interface QuoteRepository {

    suspend fun getDayQuoteRemote(): Quote?

    suspend fun getDayQuoteLocal() : Quote

    suspend fun  saveDayQuoteLocal(quote: Quote)

}