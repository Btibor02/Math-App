package com.example.mathquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.mathquest.navigation.NavGraph
import com.example.mathquest.ui.theme.MathQuestTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        populateTestData()

        setContent {
            MathQuestTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }

    private fun populateTestData() {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val scope = CoroutineScope(Dispatchers.IO)

        val initializedFlagDoc = db.collection("meta").document("initFlag")
        initializedFlagDoc.get().addOnSuccessListener { doc ->
            if (doc.exists()) return@addOnSuccessListener

            scope.launch {
                val teachers = listOf(
                    Triple("teacher1@example.com", "Teacher1Pass!", "Mr. A"),
                    Triple("teacher2@example.com", "Teacher2Pass!", "Ms. B"),
                    Triple("teacher3@example.com", "Teacher3Pass!", "Mr. C")
                )

                val teacherIds = mutableListOf<String>()

                for ((email, pass, name) in teachers) {
                    try {
                        val authResult = auth.createUserWithEmailAndPassword(email, pass).await()
                        val uid = authResult.user?.uid ?: continue
                        val teacherProfile = mapOf(
                            "uid" to uid,
                            "username" to name,
                            "email" to email
                        )
                        db.collection("teachers").document(uid).set(teacherProfile).await()
                        teacherIds.add(uid)
                    } catch (e: Exception) { }
                }

                val classes = mutableListOf<Map<String, Any>>()
                for (grade in 1..6) {
                    for (section in listOf("A", "B")) {
                        val className = "${grade}$section"
                        val joinCode = List(6) { ('A'..'Z').random() }.joinToString("")
                        val teacherId = teacherIds.random()
                        val classData = mapOf(
                            "className" to className,
                            "joinCode" to joinCode,
                            "teacherId" to teacherId
                        )
                        db.collection("classes").document(className).set(classData).await()
                        classes.add(classData)
                    }
                }

                for (classData in classes) {
                    val joinCode = classData["joinCode"] as String

                    for (i in 1..5) {
                        val username = "Student_${joinCode}_$i"
                        val email = "student_${joinCode}_$i@example.com"
                        val password = "StudentPass1!"
                        try {
                            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                            val uid = authResult.user?.uid ?: continue
                            val studentProfile = mapOf(
                                "uid" to uid,
                                "username" to username,
                                "email" to email,
                                "grade" to classData["className"],
                                "joinCode" to joinCode
                            )
                            db.collection("students").document(uid).set(studentProfile).await()
                        } catch (e: Exception) { }
                    }
                }

                initializedFlagDoc.set(mapOf("done" to true)).await()
            }
        }
    }
}
