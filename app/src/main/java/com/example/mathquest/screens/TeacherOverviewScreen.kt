package com.example.mathquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R
import com.example.mathquest.data.ClassInfo
import com.example.mathquest.data.FirestoreService
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TeacherOverviewScreen(navController: NavController, firestoreService: FirestoreService) {
    var teacherName by remember {mutableStateOf("Teacher")}
    var teacherClasses by remember { mutableStateOf(listOf<ClassInfo>()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val colorPalette = listOf(
        Color(0xFF90CAF9),
        Color(0xFFA5D6A7),
        Color(0xFFFFD54F)
    )

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            try {
                val teacher = firestoreService.getTeacherById(uid)
                teacherName = teacher?.username ?: "Teacher"

                val classes = firestoreService.getClassesByTeacher(uid)
                teacherClasses = classes
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome,\n${teacherName}!",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF214A80),
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.Start)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(Icons.Filled.Person, "12", "Students active today", Color(0xFF64B5F6))
            StatCard(Icons.Filled.CheckCircle, "45", "Tasks completed", Color(0xFF81C784))
            StatCard(Icons.Filled.Timer, "8 min", "Avg. time spent", Color(0xFFFFC107))
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "My Classes",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF214A80),
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp)
        )

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(teacherClasses) { index, classInfo ->
                val color = colorPalette[index % colorPalette.size]
                ClassCard(classInfo.className, color, navController)
            }
        }
    }
}

@Composable
fun StatCard(
    icon: ImageVector,
    value: String,
    label: String,
    iconColor: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(
                width = 3.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(value, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    Text(label, fontSize = 16.sp, color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
fun ClassCard(className: String, iconColor: Color, navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(
                width = 3.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = className,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF214A80)
                    )
                }

                Text(
                    text = "View",
                    color = Color(0xFF214A80),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                        navController.navigate("class_overview" )
                    }
                )
            }
            }
    }
}
