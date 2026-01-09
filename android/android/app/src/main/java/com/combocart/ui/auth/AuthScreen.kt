package com.combocart.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.MediumGray

@Composable
fun AuthScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PrimaryOrange.copy(alpha = 0.05f),
                        White
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/Icon
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(PrimaryOrange, RoundedCornerShape(30.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "🍽️",
                    fontSize = 60.sp
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Welcome Text
            Text(
                "Welcome to ComboCart",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Black,
                textAlign = TextAlign.Center
            )
            
            Text(
                "Your culinary journey starts here",
                fontSize = 16.sp,
                color = MediumGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(60.dp))
            
            // Login Button
            Button(
                onClick = {
                    navController.navigate(Screen.Login.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryOrange
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    "Get Started",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }
            
            Spacer(modifier = Modifier.height(80.dp))
            
            // Terms and Privacy
            Text(
                "By continuing, you agree to our\nTerms of Service and Privacy Policy",
                fontSize = 12.sp,
                color = MediumGray,
                textAlign = TextAlign.Center
            )
        }
    }
}
