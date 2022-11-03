package antuere.data.preferences_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import antuere.domain.dto.Quote
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class QuoteDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val myDataStore: DataStore<Preferences> = context.datastore

    companion object {
        val QUOTE_AUTHOR_KEY = stringPreferencesKey("author_quote")
        val QUOTE_TEXT_KEY = stringPreferencesKey("text_quote")
    }

    suspend fun saveQuote(text: String, author: String) {
        myDataStore.edit { preferences ->
            preferences[QUOTE_TEXT_KEY] = text
            preferences[QUOTE_AUTHOR_KEY] = author
        }
    }

    suspend fun getSavedQuote(): Quote {
        val author: String = myDataStore.data.map {
            it[QUOTE_AUTHOR_KEY] ?: ""
        }.first()

        val text: String = myDataStore.data.map {
            it[QUOTE_TEXT_KEY] ?: ""
        }.first()

        return Quote(text, author)
    }

}

