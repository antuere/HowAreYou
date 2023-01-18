package antuere.data.repository

import antuere.data.preferences_data_store.quote_data_store.QuoteDataStore
import antuere.data.remote.NetworkInfo
import antuere.data.remote.remote_day_database.entities.QuoteEntity
import antuere.data.remote.remote_day_database.mapping.QuoteEntityMapper
import antuere.data.remote.remote_day_database.FirebaseRealtimeDB
import antuere.domain.dto.Quote
import antuere.domain.repository.QuoteRepository
import antuere.domain.util.TimeUtility
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val firebaseApi: FirebaseRealtimeDB,
    private val networkInfo: NetworkInfo,
    private val quoteDataStore: QuoteDataStore,
    private val quoteMapper: QuoteEntityMapper
) : QuoteRepository {

    override suspend fun updateQuoteRemote(): Boolean {

        if (networkInfo.isNetworkAvailable()) {
            val currentDayOfMonth = TimeUtility.getDayOfMonth()
            val quotesNode = firebaseApi.getQuotesNode()

            val remoteQuote = quotesNode
                .child(currentDayOfMonth)
                .get().await().getValue(QuoteEntity::class.java)

            val defaultQuote = QuoteEntity("default quote", "default author")

            val latestQuote = quoteMapper.mapToDomainModel(remoteQuote ?: defaultQuote)
            saveDayQuoteLocal(latestQuote)

            delay(100)

            return true
        }
        return false
    }

    override suspend fun getDayQuoteLocal(): Quote {
        return quoteDataStore.quoteConfiguration.load()
    }

    override suspend fun saveDayQuoteLocal(quote: Quote) {
        quoteDataStore.quoteConfiguration.set(quote)
    }
}