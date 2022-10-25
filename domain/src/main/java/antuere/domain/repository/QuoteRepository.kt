package antuere.domain.repository

import antuere.domain.dto.Quote

interface QuoteRepository {

    suspend fun getDayQuote(): Quote?

}