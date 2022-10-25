package antuere.data.repository

import antuere.data.remote_day_database.FirebaseApi
import antuere.domain.dto.Quote
import antuere.domain.repository.QuoteRepository
import antuere.domain.util.TimeUtility
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val firebaseApi: FirebaseApi
) : QuoteRepository {

    override suspend fun getDayQuote(): Quote? {
        val currentDayOfMonth = TimeUtility.getDayOfMonth()
        val quotesNode = firebaseApi.getQuotesNode()

        return quotesNode
            .child(currentDayOfMonth)
            .get().await().getValue(Quote::class.java)

    }
}