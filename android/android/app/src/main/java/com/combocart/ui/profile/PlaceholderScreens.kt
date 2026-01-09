package com.combocart.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.theme.White

@Composable
fun BadgesScreen(navController: NavController) {
    PlaceholderScreen(navController, "My Badges", "Track your cooking path and earn rewards!")
}

@Composable
fun FavoritesScreen(navController: NavController) {
    PlaceholderScreen(navController, "Saved Recipes", "Your curated collection of favorite combos.")
}

@Composable
fun PlaceholderScreen(navController: NavController, title: String, subtitle: String) {
    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f))
            
            Text("🍱", fontSize = 80.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(subtitle, fontSize = 16.sp, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            
            Spacer(modifier = Modifier.weight(1.5f))
        }
    }
}
