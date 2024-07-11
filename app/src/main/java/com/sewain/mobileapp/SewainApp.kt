package com.sewain.mobileapp

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sewain.mobileapp.data.local.preferences.SessionPreferences
import com.sewain.mobileapp.ui.component.bottomnav.HomeBottomNavBar
import com.sewain.mobileapp.ui.navigation.Screen
import com.sewain.mobileapp.ui.screen.login.LoginScreen
import com.sewain.mobileapp.ui.screen.register.RegisterScreen

@Composable
fun SewainApp(
    sessionPreferences: SessionPreferences,
    navController: NavHostController = rememberNavController(),
) {
    // Retrieve the session
    val sessionModel = sessionPreferences.getSession().collectAsState(initial = null).value
    val snackbarHostState = remember { SnackbarHostState() }

    if (sessionModel != null) {
        // Determine the start destination
        val startDestination =
            if (sessionModel.token.isNotEmpty()) {
                Screen.Home.route
            } else {
                Screen.Login.route
            }

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    navController = navController,
                    snackbarHostState = snackbarHostState
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navigateToLogin = {
                        navController.popBackStack()
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
            composable(Screen.Home.route) {
                HomeBottomNavBar(
                    sessionModel = sessionModel,
                    snackbarHostState = snackbarHostState,
                )
            }
        }
    }
}

