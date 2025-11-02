package com.example.mathquest.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R
import com.example.mathquest.data.FirestoreService
import com.example.mathquest.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val firestoreService = remember { FirestoreService() }
    var isLoading by remember { mutableStateOf(false) }


    val auth = FirebaseAuth.getInstance()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Login",
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            color = Color(0xFF214A80)
        )

        Spacer(modifier = Modifier.height(60.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 40.sp)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 40.sp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(icon, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                }
            }
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    error = "Please fill in all fields"
                    return@Button
                }
                scope.launch {
                    val result = firestoreService.signIn(email, password)
                    isLoading = false

                    result.fold(
                        onSuccess = { uid ->
                            val db = FirebaseFirestore.getInstance()
                            db.collection("students").document(uid).get()
                                .addOnSuccessListener { studentDoc ->
                                    if (studentDoc.exists()) {
                                        val grade = studentDoc.getString("grade") ?: "1A"
                                        navController.navigate("grade_selection/$grade") {
                                            popUpTo(Screen.Login.route) { inclusive = true }
                                        }
                                    } else {
                                        db.collection("teachers").document(uid).get()
                                            .addOnSuccessListener { teacherDoc ->
                                                if (teacherDoc.exists()) {
                                                    navController.navigate(Screen.TeacherOverview.route) {
                                                        popUpTo(Screen.Login.route) { inclusive = true }
                                                    }
                                                } else {
                                                    error = "User not found in students or teachers"
                                                }
                                            }
                                            .addOnFailureListener { e ->
                                                error = "Failed to fetch teacher info: ${e.message}"
                                            }
                                    }
                                }
                                .addOnFailureListener { e ->
                                    error = "Failed to fetch user grade: ${e.message}"
                                }

                        },
                        onFailure = { e ->
                            error = when (e) {
                                is FirebaseAuthInvalidUserException -> "No account found with this email."
                                is FirebaseAuthInvalidCredentialsException -> "Invalid password. Please try again."
                                else -> e.message ?: "Login failed. Please try again."
                            }
                        }
                    )
                }
            },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4DA6FF)
            )
        ) {
            Text(
                "Log in",
                fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                fontSize = 40.sp
            )
        }

        error?.let {
            Spacer(modifier = Modifier.height(20.dp))
            Text(it, color = Color.Red, textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.height(30.dp))

        SignUpPrompt(navController = navController)
    }
}

@Composable
fun SignUpPrompt(navController: NavController) {
    val annotatedString = buildAnnotatedString {
        append("New here? ")
        pushStringAnnotation(tag = "SIGN_UP", annotation = "sign_up")
        withStyle(
            style = SpanStyle(
                color = Color(0xFF214A80),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Medium
            )
        ) {
            append("Sign up")
        }
        pop()
    }

    Text(
        text = annotatedString,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        modifier = Modifier.clickable {
            navController.navigate(Screen.Registration.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    )
}