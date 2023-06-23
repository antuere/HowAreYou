package antuere.how_are_you.util.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun <T> ((T) -> Unit).toStable() = remember { this }

@Composable
fun <T, V> ((T, V) -> Unit).toStable() = remember { this }

@Composable
fun <T> Iterable<T>.toStable(key: Any? = null): ImmutableList<T> {
    return remember(key) { this.toImmutableList() }
}

@Composable
fun <T> Collection<T>.toStable(key: Any? = null): SnapshotStateList<T> {
    return remember(key) {
        SnapshotStateList<T>().also { it.addAll(this) }
    }
}
