package com.example.mathquest.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mathquest.R
import com.example.mathquest.navigation.Screen

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "MATHQUEST",
            modifier = Modifier
                .padding(top = 60.dp),
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
            color = Color(0xFF214A80)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Image(
            painter = painterResource(id = R.drawable.main_screen_pic_without_background1),
            contentDescription = "Logo",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { navController.navigate(Screen.Login.route) },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4DA6FF)
            )
        ) {
            Text(
                "Log in",
                fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                fontSize = 40.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {navController.navigate(Screen.Registration.route) },
            modifier = Modifier
                .height(80.dp)
                .width(350.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(15.dp)),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFFFFF)
            )
        ) {
            Text(
                "Register",
                fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                fontSize = 40.sp,
                color = Color(0xFF214A80)
            )
        }
    }
}
