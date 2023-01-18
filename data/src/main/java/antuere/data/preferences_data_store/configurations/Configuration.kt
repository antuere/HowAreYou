package antuere.data.preferences_data_store.configurations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

abstract class Configuration<T>(protected val dataStore: DataStore<Preferences>) {
    abstract val flow: Flow<T>
    abstract suspend fun load(): T
    abstract suspend fun set(value: T)
    abstract suspend fun reset()
}