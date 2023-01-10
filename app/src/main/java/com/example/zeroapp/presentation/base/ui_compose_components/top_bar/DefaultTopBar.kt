package com.example.zeroapp.presentation.base.ui_compose_components.top_bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    @StringRes titleId: Int,
    navigationIcon: ImageVector? = null,
    navigationOnClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            navigationIcon?.apply {
                IconButton(onClick = { navigationOnClick() }) {
                    Icon(
                        imageVector = this,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        title = {
            Text(
                text = stringResource(id = titleId),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}