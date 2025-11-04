package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R
import com.example.mathquest.data.FirestoreService
import com.google.firebase.auth.FirebaseAuth

@Composable
fun StudentMenuScreen(navController: NavController, firestoreService: FirestoreService) {
    var studentName by remember {mutableStateOf("Student")}
    var studentGrade: Int? by remember { mutableStateOf(1) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            try {
                val student = firestoreService.getStudentById(uid)
                studentName = student?.username ?: "Student"

                val gradeString = student?.grade ?: "1"
                studentGrade = gradeString.filter { it.isDigit() }.toIntOrNull()
            } catch (e: Exception) {
                error = e.message
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome, ${studentName}!",
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            color = Color(0xFF214A80),
        )

        Spacer(Modifier.height(70.dp))

        Button(
            onClick = { navController.navigate("practice_setup/$studentGrade") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4DA6FF)),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text("Practice", fontSize = 40.sp, fontFamily = FontFamily(Font(R.font.nunito_extrabold)))
        }

        Button(
            onClick = { /* Future implementation */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4DA6FF)),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            enabled = false
        ) {
            Text("Homework", fontSize = 40.sp, fontFamily = FontFamily(Font(R.font.nunito_extrabold)))
        }

        Button(
            onClick = { /* TODO */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4DA6FF)),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text("Leaderboard", fontSize = 40.sp, fontFamily = FontFamily(Font(R.font.nunito_extrabold)))
        }
    }
}
