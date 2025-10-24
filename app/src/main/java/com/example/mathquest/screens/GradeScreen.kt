package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GradeSelectionScreen(navController: NavController) {
    val grades: List<Triple<String, ImageVector, Color>> = listOf(
        Triple("1. grade", Icons.Filled.School, Color(0xFFFFF176)),
        Triple("2. grade", Icons.Filled.Calculate, Color(0xFFA5D6A7)),
        Triple("3. grade", Icons.Filled.Computer, Color(0xFFFFAB91)),
        Triple("4. grade", Icons.Filled.Straighten, Color(0xFF90CAF9)),
        Triple("5. grade", Icons.AutoMirrored.Filled.MenuBook, Color(0xFFCE93D8))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp)
        ) {
            Text(
                text = "Choose your grade!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )
            Spacer(Modifier.height(20.dp))
            grades.forEach { (text: String, icon: ImageVector, color: Color) ->
                Button(
                    onClick = { navController.navigate("topic") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = color),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(icon, contentDescription = null, tint = Color(0xFF0D47A1))
                    Spacer(Modifier.width(12.dp))
                    Text(text, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
