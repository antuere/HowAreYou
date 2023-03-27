package antuere.how_are_you.presentation.base.ui_compose_components.top_bar

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
    isVisible: Boolean,
    navigationIcon: ImageVector? = null,
    navigationOnClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    if (isVisible) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                navigationIcon?.apply {
                    IconButton(onClick = { navigationOnClick() }) {
                        Icon(
                            imageVector = this,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            title = {
                Text(
                    text = stringResource(id = titleId),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            actions = actions,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}