package com.example.zeroapp.presentation.base.ui_compose_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.zeroapp.presentation.base.ui_theme.Gray800
import com.example.zeroapp.presentation.base.ui_theme.TealMain
import com.example.zeroapp.presentation.base.ui_theme.Typography


@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val iconsTitle = listOf("Home", "History", "Settings")
    val destinations = listOf(Screen.HomeScreen, Screen.HistoryScreen, Screen.Settings)

    val iconsOutline =
        listOf(Icons.Outlined.Home, Icons.Outlined.History, Icons.Outlined.Settings)
    val iconsFilled = listOf(Icons.Filled.Home, Icons.Filled.History, Icons.Filled.Settings)

    NavigationBar(
        containerColor = TealMain,
        contentColor = Color.White
    ) {
        iconsTitle.forEachIndexed { index, dest ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.route == destinations[index].route } == true

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) iconsFilled[index] else iconsOutline[index],
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = dest,
                        style = if (isSelected) Typography.displayMedium.copy(
                            fontSize = 15.sp
                        ) else Typography.displaySmall.copy(fontSize = 14.sp)
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(destinations[index].route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    indicatorColor = Color.White,
                    unselectedIconColor = Gray800,
                    unselectedTextColor = Gray800
                ),
            )
        }
    }
}