package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R

@Composable
fun ClassOverviewScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Class 5B - Overview",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF214A80),
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(vertical = 20.dp)
        )

        Spacer(Modifier.height(8.dp))

        SearchBar()

        Spacer(Modifier.height(24.dp))

        StudentTable()

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = {/* TODO */},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(60.dp)
        ) {
            Text(
                "Assign Homework",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun SearchBar() {
    var query by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = query,
        onValueChange = {query = it},
        placeholder = { Text("Search student") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color(0xFF214A80)
            )
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(3.dp, Color.Gray, RoundedCornerShape(16.dp)),
    )
}

@Composable
fun StudentTable() {
    val students = listOf(
        Triple("Emma", 14, 22),
        Triple("Leo", 9, 18),
        Triple("Teo", 12, 21),
        Triple("Joe", 10, 20)
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, Color.Gray, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Name",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF214A80)
                )
                Text(
                    "Logins",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF214A80)
                )
                Text(
                    "Task Completed",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF214A80)
                )
            }

            Spacer(Modifier.height(8.dp))

            students.forEach { (name, logins, completed) ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        name,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Text(
                        logins.toString(),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Text(
                        completed.toString(),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}