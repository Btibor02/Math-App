package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.data.LeaderboardManager

@Composable
fun LeaderboardScreen(navController: NavController) {
    val bestScores = LeaderboardManager.getBestNScores(5)
    val worstScores = LeaderboardManager.getLowestNScores(5)
    val lastScores = LeaderboardManager.getLastNScores(5)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Leaderboard",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                // use default system font to avoid missing resource errors
                fontFamily = FontFamily.Default,
                color = Color(0xFF214A80)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                "🏆 Top 5 Scores",
                fontSize = 28.sp,
                color = Color(0xFF214A80),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            )
            LeaderboardList(bestScores)

            Spacer(Modifier.height(24.dp))

            Text(
                "📉 Lowest 5 Scores",
                fontSize = 28.sp,
                color = Color(0xFF214A80),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            )
            LeaderboardList(worstScores)

            Spacer(Modifier.height(24.dp))

            Text(
                "🕒 Last 5 Played",
                fontSize = 28.sp,
                color = Color(0xFF214A80),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            )
            LeaderboardList(lastScores)
        }
    }
}

@Composable
fun LeaderboardList(scores: List<LeaderboardManager.ScoreEntry>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 250.dp)
            .padding(horizontal = 12.dp)
    ) {
        items(scores) { entry ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = entry.name,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Default,
                        color = Color(0xFF214A80)
                    )
                    Text(
                        text = entry.score.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF0D47A1)
                    )
                }
            }
        }
    }
}
