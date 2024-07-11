package com.sewain.mobileapp.ui.component.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import com.sewain.mobileapp.ui.navigation.Screen

//initializing the data class with default parameters
data class BottomNavItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screen.Home.route
            ),
            BottomNavItem(
                label = "Transaction",
                icon = Icons.AutoMirrored.Filled.List,
                route = Screen.ListTransaction.route
            ),
            BottomNavItem(
                label = "Notification",
                icon = Icons.Filled.Notifications,
                route = Screen.Notification.route
            ),
            BottomNavItem(
                label = "Profile",
                icon = Icons.Filled.AccountCircle,
                route = Screen.Profile.route
            ),
        )
    }
}