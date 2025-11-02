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


        setContent {
            MathQuestTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
