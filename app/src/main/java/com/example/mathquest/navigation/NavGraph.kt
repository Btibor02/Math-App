package com.example.mathquest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mathquest.screens.ClassOverviewScreen
import com.example.mathquest.screens.MainScreen
import com.example.mathquest.screens.LoginScreen
import com.example.mathquest.screens.RegistrationScreen
import com.example.mathquest.screens.TeacherOverviewScreen
import com.example.mathquest.screens.TopicSelectionScreen
import androidx.navigation.NavType
import com.example.mathquest.data.FirestoreService
import com.example.mathquest.screens.MathExerciseScreen
import com.example.mathquest.screens.StudentMenuScreen


sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Login : Screen("login")
    object Registration : Screen("registration")
    object TopicSelection : Screen("topic_selection")
    object TeacherOverview : Screen("teacher_overview")
    object ClassOverview : Screen("class_overview")
    object MathExercise : Screen("math_exercise")
    object StudentMenu : Screen("student_menu")
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

        composable(Screen.TopicSelection.route) {
            TopicSelectionScreen(navController)
        }
        composable(Screen.TeacherOverview.route) {
            TeacherOverviewScreen(navController, firestoreService)
        }
        composable("class_overview/{className}") { backStackEntry ->
            val className = backStackEntry.arguments?.getString("className") ?: "Unknown"
            ClassOverviewScreen(navController, className)
        }
        composable("math_exercise/{topic}") { backStackEntry ->
            val topic = backStackEntry.arguments?.getString("topic") ?: "Math"
            MathExerciseScreen(navController, topic)
        }
        composable(Screen.StudentMenu.route) {
            StudentMenuScreen(navController)
        }
    }
}