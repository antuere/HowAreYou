package antuere.data.preferences_data_store.configurations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import antuere.domain.dto.Quote
import antuere.domain.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class QuoteConfiguration(
    dataStore: DataStore<Preferences>
) : Configuration<Quote>(dataStore) {

    private val authorPreferencesKey = stringPreferencesKey(Constants.QUOTE_AUTHOR_KEY)

    private val textPreferencesKey =
        stringPreferencesKey(Constants.QUOTE_TEXT_KEY)

    override val flow: Flow<Quote> = dataStore.data.map { preferences ->
        val author = preferences[authorPreferencesKey] ?: "Walt Disney"
        val text = preferences[textPreferencesKey] ?: "If you can dream about it, you can do it"

        Quote(text, author)
    }


    override suspend fun load(): Quote {
        return flow.first()
    }

    override suspend fun reset() {
        dataStore.edit { preferences ->
            preferences.remove(authorPreferencesKey)
            preferences.remove(textPreferencesKey)
        }
    }

    override suspend fun set(value: Quote) {
        dataStore.edit { preferences ->
            preferences[authorPreferencesKey] = value.author
            preferences[textPreferencesKey] = value.text
        }
    }
}