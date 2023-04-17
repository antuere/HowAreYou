package antuere.how_are_you.presentation.base.ui_compose_components.bottom_nav_bar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.navigation.Screen

@Composable
fun DefaultBottomNavBar(navController: NavController, isVisible: Boolean) {
    if (isVisible) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val destinations = listOf(Screen.Home, Screen.History, Screen.Settings)
        val iconsTitle = listOf(
            stringResource(id = R.string.home),
            stringResource(id = R.string.history),
            stringResource(id = R.string.settings)
        )

        val iconsOutline =
            listOf(Icons.Outlined.Home, Icons.Outlined.History, Icons.Outlined.Settings)
        val iconsFilled = listOf(Icons.Filled.Home, Icons.Filled.History, Icons.Filled.Settings)

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            iconsTitle.forEachIndexed { index, dest ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == destinations[index].route } == true

                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val scaleMenuItem by animateFloatAsState(if (isPressed) 0.95f else 1f)

                NavigationBarItem(
                    modifier = Modifier.graphicsLayer {
                        scaleY = scaleMenuItem
                        scaleX = scaleMenuItem
                    },
                    interactionSource = interactionSource,
                    icon = {
                        Icon(
                            imageVector = if (isSelected) iconsFilled[index] else iconsOutline[index],
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = dest,
                            style = if (isSelected) MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 15.sp,
                            ) else MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 14.sp
                            )
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(destinations[index].route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }
        }
    }
}