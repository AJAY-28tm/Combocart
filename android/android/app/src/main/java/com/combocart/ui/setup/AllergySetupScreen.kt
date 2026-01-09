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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import com.combocart.ui.theme.LightGray
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AllergySetupScreen(navController: NavController) {
    val selectedAllergies = remember { mutableStateListOf<String>() }
    var searchQuery by remember { mutableStateOf("") }
    
    val allAllergies = listOf(
        "Peanuts", "Tree Nuts", "Milk", "Eggs", "Wheat", "Soy",
        "Fish", "Shellfish", "Gluten", "Sesame", "Mustard", "Celery"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Allergy Setup",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
        Text(
            text = "Help us keep you safe by excluding ingredients",
            fontSize = 14.sp,
            color = MediumGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search ingredients...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryOrange,
                unfocusedBorderColor = LightGray
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            allAllergies.filter { it.contains(searchQuery, ignoreCase = true) }.forEach { allergy ->
                val isSelected = selectedAllergies.contains(allergy)
                Box(
                    modifier = Modifier
                        .clickable {
                            if (isSelected) selectedAllergies.remove(allergy) else selectedAllergies.add(allergy)
                        }
                        .background(
                            color = if (isSelected) PrimaryOrange else White,
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) PrimaryOrange else Color.LightGray,
                            shape = CircleShape
                        )
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = allergy,
                        color = if (isSelected) White else Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(Screen.KitchenReady.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Get It", color = White)
        }
    }
}
