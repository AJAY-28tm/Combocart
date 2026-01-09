package com.combocart.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.MediumGray
import androidx.compose.ui.text.style.TextAlign
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White
import com.combocart.ui.theme.RedAlert
import com.combocart.ui.theme.YellowRating
import com.combocart.ui.theme.LightGray

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF9F9F9)) // Light grey background like image
                .verticalScroll(rememberScrollState())
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier.padding(16.dp)) {
                
                // Profile Main Card
                androidx.compose.material3.Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = White),
                    elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar & Badge
                        Box(contentAlignment = Alignment.BottomCenter) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(LightGray, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                // Priya Sharma Image Placeholder
                                Text("👤", fontSize = 48.sp)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        var name by remember(state.user) { mutableStateOf(state.user?.full_name ?: "Priya Sharma") }
                        var isEditing by remember { mutableStateOf(false) }
                        
                        if (isEditing) {
                            androidx.compose.material3.OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                modifier = Modifier.fillMaxWidth(0.8f),
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RedAlert),
                                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = RedAlert,
                                    unfocusedBorderColor = MediumGray
                                ),
                                trailingIcon = {
                                    IconButton(onClick = { 
                                        isEditing = false 
                                        viewModel.updateProfile(name, state.user?.email, state.user?.phone)
                                    }) {
                                        Icon(Icons.Default.Check, "Save", tint = PrimaryOrange)
                                    }
                                }
                            )
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = name, 
                                    fontSize = 24.sp, 
                                    fontWeight = FontWeight.Bold, 
                                    color = RedAlert
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = { isEditing = true },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit, 
                                        contentDescription = "Edit Name", 
                                        tint = MediumGray, 
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Level Badge
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFFD54F), RoundedCornerShape(20.dp))
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text("Level 12", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = White)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) {
                                Icon(Icons.Default.Star, null, tint = YellowRating, modifier = Modifier.size(20.dp))
                            }
                            Text(" (1089)", fontSize = 12.sp, color = MediumGray)
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ProfileStatItem("47", "Dishes Cooked")
                            ProfileStatItem("3", "Badges Earned")
                            ProfileStatItem("4.9", "Avg Rating")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text("Recent Achievements", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AchievementCard("🍳", "Italian Expert", "Cooked 10 Italian dishes")
                    AchievementCard("🌶️", "Spice King", "Mastered spicy recipes")
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Menu Items
                ProfileMenuListItem(Icons.Default.Star, "My Badges") { 
                    navController.navigate(Screen.Badges.route)
                }
                ProfileMenuListItem(Icons.Default.Face, "Combo Gold", isPremium = true) { 
                    navController.navigate(Screen.Premium.route)
                } 
                ProfileMenuListItem(Icons.Default.FavoriteBorder, "Saved Recipes") { 
                    navController.navigate(Screen.Favorites.route)
                }
                ProfileMenuListItem(Icons.Default.Settings, "Settings") { 
                    navController.navigate(Screen.Settings.route)
                }
                ProfileMenuListItem(Icons.Default.Call, "Help & Support") { 
                    navController.navigate(Screen.Support.route)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Logout
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { 
                            navController.navigate(Screen.Login.route) { popUpTo(0) }
                        }
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ExitToApp, null, tint = RedAlert, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout", color = RedAlert, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun ProfileStatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = RedAlert)
        Text(label, fontSize = 10.sp, color = MediumGray)
    }
}

@Composable
fun AchievementCard(icon: String, title: String, desc: String) {
    androidx.compose.material3.Card(
        modifier = Modifier.size(width = 150.dp, height = 180.dp),
        shape = RoundedCornerShape(16.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = White),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(icon, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text(desc, fontSize = 10.sp, color = MediumGray, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ProfileMenuListItem(icon: ImageVector, title: String, isPremium: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = MediumGray, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontWeight = FontWeight.SemiBold, color = Black)
        
        if (isPremium) {
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .background(Color(0xFFFFF9C4), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text("Premium", fontSize = 10.sp, color = Color(0xFFFBC02D), fontWeight = FontWeight.Bold)
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.KeyboardArrowRight, null, tint = MediumGray)
    }
}
