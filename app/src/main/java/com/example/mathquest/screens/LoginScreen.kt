package com.example.mathquest.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R
import com.example.mathquest.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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


        // TODO: Change to email
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
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
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate(Screen.GradeSelection.route) {
                popUpTo(Screen.Main.route) { inclusive = false }
            } },
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