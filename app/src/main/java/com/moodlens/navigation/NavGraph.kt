package com.moodlens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moodlens.ui.screens.habit.HabitScreen
import com.moodlens.ui.screens.history.HistoryScreen
import com.moodlens.ui.screens.home.HomeScreen
import com.moodlens.ui.screens.login.LoginScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
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
            LoginScreen(onLoginClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        
        composable(Screen.Home.route) {
            HomeScreen()
        }
        
        composable(Screen.Habits.route) {
            HabitScreen()
        }
        
        composable(Screen.History.route) {
            HistoryScreen()
        }
    }
}
