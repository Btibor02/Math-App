package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R
import com.example.mathquest.mathlogic.AdditionLogic
import com.example.mathquest.mathlogic.AnswerVerifier
import com.example.mathquest.mathlogic.SubstractionLogic
import com.example.mathquest.mathlogic.MultiplicationLogic
import com.example.mathquest.mathlogic.DivisionLogic
import com.example.mathquest.mathlogic.TimerLogic

@Composable
fun MathExerciseScreen(navController: NavController, topic: String) {
    var answer by remember { mutableStateOf("") }
    var answerInput by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val fontFamily = FontFamily(Font(R.font.nunito_extrabold))

    val grade = 2

    val question = remember(topic) {
        when (topic) {
            "Add" -> AdditionLogic.generateQuestion(grade)
            "Subtract" -> SubstractionLogic.generateQuestion(grade)
            "Multiply" -> MultiplicationLogic.generateQuestion(grade)
            "Divide" -> DivisionLogic.generateQuestion(grade)
            else -> "?"
        }
    }

    val topicFullNames = mapOf(
        "Add" to "Addition",
        "Subtract" to "Subtraction",
        "Multiply" to "Multiplication",
        "Divide" to "Division"
    )

    val displayName = topicFullNames[topic] ?: topic

    var remainingTime by remember { mutableStateOf(60) }
    var timeUp by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        TimerLogic.startTimer(
            seconds = 60,
            onTick = { remainingTime = it},
            onFinish = { timeUp = true }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = displayName,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF214A80),
            fontFamily = fontFamily
        )

        Spacer(Modifier.height(30.dp))

        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color(0xFFFFD54F),
            modifier = Modifier.size(48.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = question,
            fontSize = 70.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = fontFamily
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = answerInput,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() || it == '.' }) {
                    answerInput = newValue
                }
            },
            label = { Text("Your answer") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = fontFamily
            ),
            singleLine = true,
            modifier = Modifier
                .width(250.dp)
                .height(70.dp)
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                focusManager.clearFocus()
                val numericValue = answerInput.toDoubleOrNull()
                if (numericValue != null) {
                    val isCorrect = AnswerVerifier.verifyAnswer(numericValue)
                    if (isCorrect) {
                        feedback = "✅ Correct!"
                        TimerLogic.stopTimer()
                    } else {
                        feedback =  "❌ Wrong! Correct answer: ${AnswerVerifier.getRightAnswer()}"
                    }
                } else {
                    feedback = "Please enter a valid number"
                }
            },
            enabled = !timeUp,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .width(200.dp)
                .height(70.dp)
        ) {
            Text(
                "Submit",
                color = Color.Black,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
        }

        Spacer(Modifier.height(30.dp))

        Text(
            text = if (!timeUp) feedback else "⏰ Time's up!",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            color = if (feedback.startsWith("✅")) Color(0xFF2E7D32) else Color(0xFFD32F2F)
        )

        Spacer(Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(50))
                .border(2.dp, Color.Gray, RoundedCornerShape(50))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                "${remainingTime}s",
                color = Color.Black,
                fontFamily = fontFamily,
                fontSize = 18.sp
            )
        }
}
}
