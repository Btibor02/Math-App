package com.example.mathquest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mathquest.screens.GradeSelectionScreen
import com.example.mathquest.screens.MainScreen
import com.example.mathquest.screens.LoginScreen
import com.example.mathquest.screens.RegistrationScreen
import com.example.mathquest.screens.TopicSelectionScreen
import com.example.mathquest.screens.LeaderboardScreen


sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Login : Screen("login")
    object Registration : Screen("registration")
    object GradeSelection : Screen("grade_selection")
    object TopicSelection : Screen("topic_selection")
    object Leaderboard : Screen("leaderboard")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Registration.route) {
            RegistrationScreen(navController)
        }
        composable(Screen.GradeSelection.route) {
            GradeSelectionScreen(navController)
        }
        composable(Screen.TopicSelection.route) {
            TopicSelectionScreen(navController)
        }
        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(navController)
        }
    }
}