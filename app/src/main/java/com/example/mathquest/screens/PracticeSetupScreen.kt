package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeSetupScreen(
    navController: NavController,
    studentGrade: Int
) {
    val nunitoBold = FontFamily(Font(R.font.nunito_extrabold))

    val availableGrades = (1..studentGrade).toList()
    val taskCounts = listOf(10, 25, 50)
    val timeLimits = listOf(10, 30, 60)
    val operations = listOf("Addition", "Subtraction", "Multiplication", "Division")

    var selectedGrade by remember { mutableStateOf(availableGrades.last()) }
    var selectedTaskCount by remember { mutableStateOf(taskCounts.first()) }
    var selectedTime by remember { mutableStateOf(timeLimits.first()) }
    var selectedOperation by remember { mutableStateOf(operations.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Practice Setup",
            fontSize = 40.sp,
            color = Color(0xFF214A80),
            fontFamily = nunitoBold,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        DropdownSelector(
            label = "Grade",
            options = availableGrades.map { "Grade $it" },
            selectedOption = "Grade $selectedGrade",
            onSelect = { selectedGrade = it.removePrefix("Grade ").toInt() },
            fontFamily = nunitoBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        DropdownSelector(
            label = "Number of Tasks",
            options = taskCounts.map { "$it" },
            selectedOption = selectedTaskCount.toString(),
            onSelect = { selectedTaskCount = it.toInt() },
            fontFamily = nunitoBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        DropdownSelector(
            label = "Time per Task (sec)",
            options = timeLimits.map { "$it" },
            selectedOption = selectedTime.toString(),
            onSelect = { selectedTime = it.toInt() },
            fontFamily = nunitoBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        DropdownSelector(
            label = "Operation",
            options = operations,
            selectedOption = selectedOperation,
            onSelect = { selectedOperation = it },
            fontFamily = nunitoBold
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                navController.navigate(
                    "mathExercise/$selectedGrade/$selectedTaskCount/$selectedTime/$selectedOperation"
                )
            },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4DA6FF))
        ) {
            Text("Start Practice", fontFamily = nunitoBold, fontSize = 40.sp, color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onSelect: (String) -> Unit,
    fontFamily: FontFamily
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            label = { Text(label, fontFamily = fontFamily) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontFamily = fontFamily,
                fontSize = 18.sp
            )
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, fontFamily = fontFamily) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

