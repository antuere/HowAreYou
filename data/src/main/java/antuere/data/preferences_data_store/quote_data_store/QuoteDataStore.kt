package antuere.data.preferences_data_store.quote_data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import antuere.data.preferences_data_store.configurations.Configuration
import antuere.data.preferences_data_store.configurations.QuoteConfiguration
import antuere.domain.dto.Quote

class QuoteDataStore(context: Context, name: String) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name)
    private val quoteDataStore: DataStore<Preferences> = context.datastore

    val quoteConfiguration: Configuration<Quote> = QuoteConfiguration(quoteDataStore)

}

