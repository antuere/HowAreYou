package com.example.zeroapp.presentation.history.ui_compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import antuere.domain.dto.ToggleBtnState

@Composable
fun ToggleBtnGroup(
    modifier: Modifier = Modifier,
    toggleBtnState: ToggleBtnState,
    onClick: (ToggleBtnState) -> Unit
) {
    val cornerRadius = 8.dp

    val toggleMap = mapOf(
        0 to ToggleBtnState.ALL_DAYS,
        1 to ToggleBtnState.CURRENT_MONTH,
        2 to ToggleBtnState.LAST_WEEK
    )
    Row(modifier = modifier.fillMaxWidth()) {
//        toggleMap.entries.forEachIndexed { index, entry ->
//            entry.
//        }
        toggleMap.forEach { (i, btnState) ->
            OutlinedButton(onClick = {
                onClick(btnState)
            },
                shape = when (i) {
                    // left outer button
                    0 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = 0.dp,
                        bottomStart = cornerRadius,
                        bottomEnd = 0.dp
                    )
                    // right outer button
                    2 -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = cornerRadius,
                        bottomStart = 0.dp,
                        bottomEnd = cornerRadius
                    )

                    else -> RoundedCornerShape(
                        0.dp
                    )
                },
            ) {
                Text(text = "test")
            }

        }
    }
}

//private fun toggleSelected(index: Int, toggleBtnState: ToggleBtnState) {
//    when (index) {
//        0 -> toggleBtnState = ToggleBtnState.LAST_WEEK
//    }
//}