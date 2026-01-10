package com.moodlens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moodlens.ui.screens.checkin.CheckInScreen
import com.moodlens.ui.screens.habit.HabitScreen
import com.mcn.moodlens.ui.screens.history.HistoryScreen
import com.moodlens.ui.screens.home.HomeScreen
import com.mcn.moodlens.ui.screens.login.LoginScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")

    // ✅ NEW (only added)
    object Main : Screen("main")

    // ✅ OLD (kept same)
    object Home : Screen("home")
    object CheckIn : Screen("checkin")
    object Habits : Screen("habits")
    object History : Screen("history")
}

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    // ✅ If you want old behaviour use Home
                    // navController.navigate(Screen.Home.route)

                    // ✅ If you want Bottom nav later use Main
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // ✅ NEW route (only added)
        composable(Screen.Main.route) {
            // For NOW: just open Home (so no error)
            // Later we will replace this by MainScreen(bottom nav)
            HomeScreen(
                userName = "Subodh",
                onCheckInClick = {
                    navController.navigate(Screen.CheckIn.route)
                },
                onHabitsClick = {
                    navController.navigate(Screen.Habits.route)
                }
            )
        }

        // ✅ OLD routes (kept same)
        composable(Screen.Home.route) {
            HomeScreen(
                userName = "Subodh",
                onCheckInClick = {
                    navController.navigate(Screen.CheckIn.route)
                },
                onHabitsClick = {
                    navController.navigate(Screen.Habits.route)
                }
            )
        }

        composable(Screen.CheckIn.route) {
            CheckInScreen(
                onSave = {
                    navController.navigate(Screen.History.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        composable(Screen.Habits.route) {
            HabitScreen()
        }

        composable(Screen.History.route) {
            HistoryScreen()
        }
    }
}
