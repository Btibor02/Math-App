package com.example.mathquest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mathquest.screens.ClassOverviewScreen
import com.example.mathquest.screens.MainScreen
import com.example.mathquest.screens.LoginScreen
import com.example.mathquest.screens.RegistrationScreen
import com.example.mathquest.screens.TeacherOverviewScreen
import com.example.mathquest.data.FirestoreService
import com.example.mathquest.screens.MathExerciseScreen
import com.example.mathquest.screens.PracticeSetupScreen
import com.example.mathquest.screens.StudentMenuScreen
import com.example.mathquest.screens.LeaderboardScreen


sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Login : Screen("login")
    object Registration : Screen("registration")
    object TeacherOverview : Screen("teacher_overview")
    object ClassOverview : Screen("class_overview")
    object MathExercise : Screen("math_exercise")
    object StudentMenu : Screen("student_menu")
    object PracticeSetup : Screen("practice_setup")
    object Leaderboard : Screen("leaderboard")
}

@Composable
fun NavGraph(navController: NavHostController) {
    val firestoreService = remember { FirestoreService() }
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
        composable(Screen.TeacherOverview.route) {
            TeacherOverviewScreen(navController, firestoreService)
        }
        composable("class_overview/{className}") { backStackEntry ->
            val className = backStackEntry.arguments?.getString("className") ?: "Unknown"
            ClassOverviewScreen(navController, className)
        }
        composable("math_exercise/{grade}/{operation}/{questionCount}/{timePerQuestion}") { backStackEntry ->
            val grade = backStackEntry.arguments?.getString("grade")?.toIntOrNull() ?: 1
            val operation = backStackEntry.arguments?.getString("operation") ?: "Add"
            val questionCount = backStackEntry.arguments?.getString("questionCount")?.toIntOrNull() ?: 10
            val timePerQuestion = backStackEntry.arguments?.getString("timePerQuestion")?.toIntOrNull() ?: 30

            MathExerciseScreen(
                navController = navController,
                grade = grade,
                operation = operation,
                questionCount = questionCount,
                timePerQuestion = timePerQuestion
            )
        }
        composable(Screen.StudentMenu.route) {
            StudentMenuScreen(navController, firestoreService)
        }

        composable(
            "practice_setup/{studentGrade}") { backStackEntry ->
            val studentGrade = backStackEntry.arguments?.getString("studentGrade")?.toIntOrNull() ?: 1
            PracticeSetupScreen(navController, studentGrade)
        }

        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(navController)
        }
    }
}