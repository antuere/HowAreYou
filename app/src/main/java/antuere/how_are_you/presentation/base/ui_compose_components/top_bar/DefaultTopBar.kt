package antuere.how_are_you.presentation.base.ui_compose_components.top_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import antuere.how_are_you.presentation.base.ui_text.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    title: UiText? = null,
    topBarType: TopBarType,
    isVisible: Boolean,
    navigationIcon: ImageVector? = null,
    navigationOnClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    if (isVisible) {
        when (topBarType) {
            TopBarType.CENTER_ALIGNED -> {
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
                        title?.apply {
                            Text(
                                text = this.asString(),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                    actions = actions,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            TopBarType.SMALL -> {
                TopAppBar(
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
                        title?.apply {
                            Text(
                                text = this.asString(),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                    actions = actions,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            TopBarType.MEDIUM -> {
                MediumTopAppBar(
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
                        title?.apply {
                            Text(
                                text = this.asString(),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                    actions = actions,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            TopBarType.LARGE -> {
                LargeTopAppBar(
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
                        title?.apply {
                            Text(
                                text = this.asString(),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                    actions = actions,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}