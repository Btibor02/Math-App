package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.input.KeyboardType
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var username by remember {mutableStateOf("")}
    var grade by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember {mutableStateOf("")}
    var joinCode by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember {mutableStateOf(false)}

    val firestoreService = remember { FirestoreService() }
    val scope = rememberCoroutineScope()

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val grades = listOf("1", "2", "3", "4", "5", "6")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Register",
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            color = Color(0xFF214A80),
            modifier = Modifier.padding(top=10.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 30.sp)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            leadingIcon = { Icon(Icons.Default.AccountCircle, null) },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 30.sp)
        )

        Spacer(Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = grade,
                onValueChange = {},
                label = { Text("Grade", fontSize = 20.sp) },
                leadingIcon = { Icon(Icons.Default.Add, contentDescription = null) },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .height(80.dp)
                    .width(350.dp),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(fontSize = 30.sp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                grades.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                it,
                                fontSize = 30.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        },
                        onClick = {
                            grade = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(icon, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 30.sp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm password") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            trailingIcon = {
                val icon = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(icon, contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 30.sp),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = joinCode,
            onValueChange = { joinCode = it },
            label = { Text("Join code") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 30.sp)
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank() || grade.isBlank() || joinCode.isBlank()) {
                    error = "Please fill all fields"
                    return@Button
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    error = "Invalid email format"
                    return@Button
                }

                if (password != confirmPassword) {
                    error = "Passwords do not match"
                    return@Button
                }

                if (password.length < 8) {
                    error = "Password must be at least 8 characters"
                    return@Button
                }

                val hasUppercase = password.any { it.isUpperCase() }
                val hasDigit = password.any { it.isDigit() }
                val hasSpecial = password.any { !it.isLetterOrDigit() }

                if (!hasUppercase || !hasDigit || !hasSpecial) {
                    error = "Password must contain an uppercase letter, a number, and a special character"
                    return@Button
                }

                isLoading = true
                error = null
                scope.launch {
                    val result = firestoreService.registerStudent(
                        email,
                        password,
                        username,
                        grade,
                        joinCode
                    )
                    isLoading = false
                    result.fold(
                        onSuccess = {
                            navController.navigate(Screen.GradeSelection.route) {
                                popUpTo (Screen.Main.route ) { inclusive = false }
                            }
                        },
                        onFailure = { e -> error = e.message}
                    )
                }
            },
            enabled = !isLoading,
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF4DA6FF))
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text(
                    "Register",
                    fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                    fontSize = 40.sp
                )
            }

        }

        error?.let {
            Spacer(modifier = Modifier.height(20.dp))
            Text(it, color = Color.Red, textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.height(30.dp))

        SignInPrompt(navController = navController)
    }
}

@Composable
fun SignInPrompt(navController: NavController) {
    val annotatedString = buildAnnotatedString {
        append("Already have an account? ")
        pushStringAnnotation(tag = "SIGN_IN", annotation = "sign_in")
        withStyle(
            style = SpanStyle(
                color = Color(0xFF214A80),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Medium
            )
        ) {
            append("Sign in")
        }
        pop()
    }

    Text(
        text = annotatedString,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        modifier = Modifier.clickable {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Registration.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    )
}
