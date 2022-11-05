package antuere.data.repository

import antuere.data.preferences_data_store.quote_data_store.QuoteDataStore
import antuere.data.preferences_data_store.quote_data_store.entities.QuoteEntity
import antuere.data.preferences_data_store.quote_data_store.mapping.QuoteEntityMapper
import antuere.data.remote_day_database.FirebaseApi
import antuere.domain.dto.Quote
import antuere.domain.repository.QuoteRepository
import antuere.domain.util.TimeUtility
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val firebaseApi: FirebaseApi,
    private val quoteDataStore: QuoteDataStore,
    private val quoteMapper: QuoteEntityMapper
) : QuoteRepository {

    override suspend fun updateQuoteRemote(): Boolean {

        if (firebaseApi.isNetworkAvailable()) {
            val currentDayOfMonth = TimeUtility.getDayOfMonth()
            val quotesNode = firebaseApi.getQuotesNode()


            val remoteQuote = quotesNode
                .child(currentDayOfMonth)
                .get().await().getValue(QuoteEntity::class.java)


            val defaultQuote = QuoteEntity("default quote", "default author")

            val latestQuote = quoteMapper.mapToDomainModel(remoteQuote ?: defaultQuote)
            saveDayQuoteLocal(latestQuote)
            return true
        }
        return false
    }

    override suspend fun getDayQuoteLocal(): Quote {
        return quoteDataStore.getSavedQuote()
    }

    override suspend fun saveDayQuoteLocal(quote: Quote) {
        quoteDataStore.saveQuote(quote.text, quote.author)
    }
}