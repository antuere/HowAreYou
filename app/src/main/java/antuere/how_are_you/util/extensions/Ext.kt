package antuere.how_are_you.util.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import antuere.domain.dto.helplines.SupportedCountry
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

fun Context.findFragmentActivity(): FragmentActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context as FragmentActivity
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun String.upperCaseFirstCharacter(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

fun SupportedCountry.getName(): UiText {
    return when (this) {
        is SupportedCountry.Italy -> UiText.StringResource(R.string.italy)
        is SupportedCountry.Russia -> UiText.StringResource(R.string.russia)
        is SupportedCountry.USA -> UiText.StringResource(R.string.usa)
        is SupportedCountry.France -> UiText.StringResource(R.string.france)
        is SupportedCountry.China -> UiText.StringResource(R.string.china)
    }
}

fun LazyListState.animateScrollAndCentralize(index: Int, scope: CoroutineScope) {
    val itemInfo = this.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    scope.launch {
        if (itemInfo != null) {
            val center = this@animateScrollAndCentralize.layoutInfo.viewportEndOffset / 2
            val childCenter = itemInfo.offset + itemInfo.size / 2
            this@animateScrollAndCentralize.animateScrollBy((childCenter - center).toFloat())
        } else {
            this@animateScrollAndCentralize.animateScrollToItem(index)
        }
    }
}

fun LazyGridState.isScrollInInitialState(): Boolean =
    firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset == 0

fun LazyListState.isScrollInInitialState(): Boolean =
    firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset == 0

val Int.fixedSize
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

@Stable
val Float.fixedSize
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp
