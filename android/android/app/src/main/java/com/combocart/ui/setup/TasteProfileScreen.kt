package com.combocart.ui.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.DarkGray
import com.combocart.ui.theme.GreenSuccess
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

@Composable
fun TasteProfileScreen(navController: NavController) {
    var spiceLevel by remember { mutableFloatStateOf(0.5f) }
    var sweetTooth by remember { mutableFloatStateOf(0.5f) }
    var healthFocus by remember { mutableFloatStateOf(0.5f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Taste Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
        Text(
            text = "Customize your food recommendations",
            fontSize = 14.sp,
            color = MediumGray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Spice Level
        TasteSliderItem(
            label = "Spice Level",
            icon = "🌶️",
            value = spiceLevel,
            onValueChange = { spiceLevel = it },
            accentColor = Color.Red
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sweet Tooth
        TasteSliderItem(
            label = "Sweet Tooth",
            icon = "🍬",
            value = sweetTooth,
            onValueChange = { sweetTooth = it },
            accentColor = Color(0xFFFF69B4) // Pink
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Health Focus
        TasteSliderItem(
            label = "Health Focus",
            icon = "💚",
            value = healthFocus,
            onValueChange = { healthFocus = it },
            accentColor = GreenSuccess
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(Screen.DietaryPreferences.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Complete Setup", color = White)
        }
    }
}

@Composable
fun TasteSliderItem(
    label: String,
    icon: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    accentColor: Color
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = label, fontWeight = FontWeight.Medium, fontSize = 16.sp, color = DarkGray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Slider(
            value = value,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(
                thumbColor = PrimaryOrange,
                activeTrackColor = PrimaryOrange,
                inactiveTrackColor = Color.LightGray
            ),
             modifier = Modifier.fillMaxWidth()
        )
    }
}
