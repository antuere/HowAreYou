package antuere.how_are_you.presentation.screens.helplines.ui_compose.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import antuere.domain.dto.helplines.Helpline

@Composable
fun HelplinesColumn(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    helplines: SnapshotStateList<Helpline>,
    onClickPhone: (String) -> Unit,
    onClickWebsite: (String) -> Unit,
    onClickItem: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            items = helplines,
            key = { _, helpline -> helpline.nameResId }
        ) { index, helpline ->
            HelplineItem(
                helpline = helpline,
                onClickPhone = onClickPhone,
                onClickWebsite = onClickWebsite,
                onClickToItem = { onClickItem(index) }
            )
        }
    }
}