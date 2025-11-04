package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R

@Composable
fun StudentMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome, Student!",
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            color = Color(0xFF214A80),
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Button(
            onClick = { /* TODO */ },
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
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text("Leaderboard", fontSize = 40.sp, fontFamily = FontFamily(Font(R.font.nunito_extrabold)))
        }
    }
}
