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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.zeroapp.presentation.base.ui_theme.Gray
import com.example.zeroapp.presentation.base.ui_theme.TealMain
import com.example.zeroapp.presentation.base.ui_theme.Typography


@Composable
fun BottomNavBar() {
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val destTitle = listOf("Home", "History", "Settings")

    val iconsOutline =
        listOf(Icons.Outlined.Home, Icons.Outlined.History, Icons.Outlined.Settings)
    val iconsFilled = listOf(Icons.Filled.Home, Icons.Filled.History, Icons.Filled.Settings)

    NavigationBar(
        containerColor = TealMain,
        contentColor = Color.White
    ) {
        destTitle.forEachIndexed { index, dest ->
            val isSelected = selectedItem == index
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
                onClick = { selectedItem = index },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    indicatorColor = Color.White,
                    unselectedIconColor = Gray,
                    unselectedTextColor = Gray
                ),
            )
        }
    }
}