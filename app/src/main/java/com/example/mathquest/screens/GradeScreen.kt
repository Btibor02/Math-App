package com.example.mathquest.screens

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R

@Composable
fun GradeSelectionScreen(navController: NavController, userGrade: String) {
    val grades: List<Triple<String, ImageVector, Color>> = listOf(
        Triple("1. grade", Icons.Filled.School, Color(0xFFFFF176)),
        Triple("2. grade", Icons.Filled.Calculate, Color(0xFFA5D6A7)),
        Triple("3. grade", Icons.Filled.Computer, Color(0xFFFFAB91)),
        Triple("4. grade", Icons.Filled.Straighten, Color(0xFF90CAF9)),
        Triple("5. grade", Icons.AutoMirrored.Filled.MenuBook, Color(0xFFCE93D8)),
        Triple("6. grade", Icons.Filled.Star, Color(0xFFFFCC80))
    )

    val gradeNumber = userGrade.firstOrNull()?.digitToIntOrNull() ?: 1

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
                .padding(top = 60.dp)
        ) {
            Text(
                text = "Choose your grade!",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                color = Color(0xFF214A80),
                textAlign = TextAlign.Center,
                style = TextStyle(lineHeight = 70.sp)
            )
            Spacer(Modifier.height(40.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(grades.indices.toList()) { index ->
                    val (text, icon, color) = grades[index]
                    val isLocked = index + 1 > gradeNumber

                    Button(
                        onClick = { if (!isLocked) {navController.navigate("topic_selection")
                        } },
                        modifier = Modifier
                            .height(120.dp)
                            .width(350.dp)
                            .alpha(if (isLocked) 0.5f else 1f),
                        colors = ButtonDefaults.buttonColors(containerColor = color),
                        shape = RoundedCornerShape(15.dp),
                        enabled = !isLocked
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = Color(0xFF0D47A1),
                                    modifier = Modifier.size(50.dp)
                                )
                                Spacer(Modifier.width(32.dp))
                                Text(
                                    text,
                                    fontSize = 44.sp,
                                    color = Color(0xFF214A80),
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.nunito_extrabold))
                                )
                            }
                            if (isLocked) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "Locked",
                                    tint = Color(0xFF0D47A1),
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
