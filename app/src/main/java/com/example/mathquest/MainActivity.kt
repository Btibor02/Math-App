package com.example.mathquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.mathquest.data.UserProfile
import com.example.mathquest.data.UserRepository
import com.example.mathquest.navigation.NavGraph
import com.example.mathquest.ui.theme.MathQuestTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        val userRepository = UserRepository()
        /* Fill up database with example data
        populateTestUsers(userRepository)
        */
        setContent {
            MathQuestTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

fun populateTestUsers(userRepository: UserRepository) {
    val users = listOf(
        Triple("user1@example.com", "password1", false),
        Triple("user2@example.com", "password1", false),
        Triple("user3@example.com", "password1", false),
        Triple("user4@example.com", "password1", false),
        Triple("user5@example.com", "password1", false),
        Triple("user6@example.com", "password1", false),
        Triple("user7@example.com", "password1", false),
        Triple("user8@example.com", "password1", false),
        Triple("user9@example.com", "password1", false),
        Triple("teacher1@example.com", "password2", true)
    )

    CoroutineScope(Dispatchers.IO).launch {
        users.forEach { (email, password, isTeacher) ->
            val profile = UserProfile(
                uid = "",
                username = email.substringBefore("@"),
                grade = "5",
                joinCode = "ABC123",
                isTeacher = isTeacher
            )

            val result = userRepository.registerWithEmail(email, password, profile)
            if (result.isSuccess) {
                println("Created user: $email, teacher: $isTeacher")
            } else {
                println("Failed to create user $email: ${result.exceptionOrNull()?.message}")
            }
        }
    }
}
