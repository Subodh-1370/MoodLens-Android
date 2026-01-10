package com.moodlens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moodlens.ui.screens.checkin.CheckInScreen
import com.moodlens.ui.screens.habit.HabitScreen
import com.mcn.moodlens.ui.screens.history.HistoryScreen
import com.moodlens.ui.screens.home.HomeScreen

@Composable
fun MainScreen(userName: String = "Subodh") {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Home.route) {
                HomeScreen(
                    userName = userName,
                    onCheckInClick = { navController.navigate(Screen.CheckIn.route) },
                    onHabitsClick = { navController.navigate(Screen.Habits.route) }
                )
            }

            composable(Screen.CheckIn.route) {
                CheckInScreen(
                    onSave = {
                        navController.navigate(Screen.History.route)
                    }
                )
            }

            composable(Screen.History.route) {
                HistoryScreen()
            }

            composable(Screen.Habits.route) {
                HabitScreen()
            }
        }
    }
}
