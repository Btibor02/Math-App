package com.example.mathquest.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R
import com.example.mathquest.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var joinCode by remember { mutableStateOf("") }

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
            color = Color(0xFF214A80)
        )

        Spacer(modifier = Modifier.height(60.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(fontSize = 30.sp),
            visualTransformation = PasswordVisualTransformation()
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
            onClick = { navController.navigate(Screen.Main.route) },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4DA6FF)
            )
        ) {
            Text(
                "Register",
                fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                fontSize = 40.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        TextButton({ navController.navigate(Screen.Login.route) }) {
            Text("Already have an account? Log in", color = Color.Black)
        }
    }
}
