package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R

@Composable
fun TopicSelectionScreen(navController: NavController) {
    val topics: List<Triple<String, ImageVector, Color>> = listOf(
        Triple("Add", Icons.Filled.Add, Color(0xFFFFF176)),
        Triple("Subtract", Icons.Filled.HorizontalRule, Color(0xFFA5D6A7)),
        Triple("Multiply", Icons.Filled.Clear, Color(0xFFFFAB91)),
        Triple("Divide", Icons.Filled.Percent, Color(0xFF90CAF9))
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
                .padding(top = 60.dp)
        ) {
            Text(
                text = "Choose a topic!",
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
                items(topics.indices.toList()) { index ->
                    val (text, icon, color) = topics[index]
                    val isLocked = index != 0

                    Button(
                        onClick = { if (!isLocked) { /* TODO: Navigate to exercises */ } },
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
