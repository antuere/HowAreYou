package antuere.data.repository

import antuere.data.network.NetworkInfo
import antuere.data.network.remote_day_database.FirebaseRealtimeDB
import antuere.data.network.remote_day_database.entities.QuoteEntity
import antuere.data.network.remote_day_database.mapping.QuoteEntityMapper
import antuere.data.preferences_data_store.quote_data_store.QuoteDataStore
import antuere.domain.dto.Quote
import antuere.domain.repository.QuoteRepository
import antuere.domain.util.TimeUtility
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val firebaseApi: FirebaseRealtimeDB,
    private val networkInfo: NetworkInfo,
    private val quoteDataStore: QuoteDataStore,
    private val quoteMapper: QuoteEntityMapper,
) : QuoteRepository {

    override suspend fun updateQuoteRemote() {
        if (networkInfo.isNetworkAvailable()) {
            withTimeoutOrNull(2500L) {
                val currentDayOfMonth = TimeUtility.getDayOfMonth()
                val quotesNode = firebaseApi.getQuotesNode()

                val remoteQuote = quotesNode
                    .child(currentDayOfMonth)
                    .get().await().getValue(QuoteEntity::class.java)

                val defaultQuote = QuoteEntity("default quote", "default author")

                val latestQuote = quoteMapper.mapToDomainModel(remoteQuote ?: defaultQuote)
                saveDayQuoteLocal(latestQuote)
            }
        }
    }

    override suspend fun getDayQuoteLocal(): Quote {
        return quoteDataStore.quoteConfiguration.load()
    }

    override suspend fun saveDayQuoteLocal(quote: Quote) {
        quoteDataStore.quoteConfiguration.set(quote)
    }
}