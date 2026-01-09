package com.combocart.ui.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.DarkGray
import com.combocart.ui.theme.GreenSuccess
import com.combocart.ui.theme.LightGreen
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.RedAlert
import com.combocart.ui.theme.White
import com.combocart.ui.theme.YellowRating

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DietaryPreferencesScreen(navController: NavController) {
    val selectedOptions = remember { mutableStateListOf<String>() }
    
    val options = listOf(
        DietOption("Vegetarian", "🥬", GreenSuccess),
        DietOption("Non-Vegetarian", "🍗", RedAlert),
        DietOption("Vegan", "🌱", GreenSuccess),
        DietOption("Jain", "🕉️", YellowRating)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // Gradient Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(LightGreen.copy(alpha = 0.3f), White)
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dietary Preferences",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Text(
                    text = "Select all that apply to personalize your experience",
                    fontSize = 14.sp,
                    color = MediumGray
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                maxItemsInEachRow = 2
            ) {
                options.forEach { option ->
                    val isSelected = selectedOptions.contains(option.label)
                    DietaryCard(
                        option = option,
                        isSelected = isSelected,
                        modifier = Modifier
                            .weight(1f)
                            .height(140.dp)
                            .clickable {
                                if (isSelected) selectedOptions.remove(option.label) else selectedOptions.add(option.label)
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(Screen.AllergySetup.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Continue", color = White)
        }
    }
}

data class DietOption(val label: String, val icon: String, val color: Color)

@Composable
fun DietaryCard(
    option: DietOption,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) option.color else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .background(if (isSelected) option.color.copy(alpha = 0.1f) else White, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White, CircleShape)
                    .border(1.dp, Color.LightGray.copy(alpha=0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = option.icon, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = option.label,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = DarkGray
            )
        }
        
        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
                    .background(option.color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
