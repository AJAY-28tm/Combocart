package com.combocart.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import com.combocart.ui.home.HomeViewModel
import com.combocart.ui.home.HomeState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.DarkGray
import com.combocart.ui.theme.GreenSuccess
import com.combocart.ui.theme.LightGray
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White
import com.combocart.ui.theme.YellowRating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.refreshLocation()
    }

    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            item {
                HomeHeader(navController, state.location)
            }

            // Search Bar & Filters
            item {
                SearchBarSection(navController)
            }

            // Meal Times Section (NEW)
            item {
                MealTimesSection(navController)
            }

            // Cravings Section
            item {
                CravingsSection(navController)
            }

            // Achievements
            item {
                AchievementsSection()
            }

            // Deal of the Day
            item {
                DealOfTheDaySection()
            }

            // Retail Banner
            item {
                RetailBanner(navController)
            }

            // Trending Now (Real Data)
            item {
                TrendingSection(
                    navController = navController,
                    state = state
                )
            }
            
            // Spacer for bottom nav
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun HomeHeader(navController: NavController, location: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryOrange, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Welcome to CC", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Row(
                modifier = Modifier.clickable { navController.navigate(Screen.LocationSetup.route) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = location, 
                    fontSize = 11.sp, 
                    color = MediumGray.copy(alpha = 0.8f), 
                    lineHeight = 14.sp,
                    maxLines = 2,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = MediumGray, modifier = Modifier.size(16.dp))
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Retail Chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(PrimaryOrange)
                    .clickable { navController.navigate(Screen.RetailHome.route) }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Store, null, tint = White, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Retail", color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            IconButton(onClick = { navController.navigate(Screen.Notifications.route) }) {
                Box {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    // Badge as in image
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color.Red, CircleShape)
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSection(navController: NavController) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightGray, RoundedCornerShape(8.dp))
                .clickable { navController.navigate(Screen.Search.route) }
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Search, contentDescription = null, tint = MediumGray)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search for dishes, ingredients...", color = MediumGray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filters
        var selectedFilter by remember { mutableStateOf("All Combos") }
        val filters = listOf("All Combos", "Vegetarian", "Non-Vegetarian")

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filters) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryOrange,
                        selectedLabelColor = White,
                        containerColor = White,
                        labelColor = DarkGray
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (selectedFilter == filter) PrimaryOrange else Color.LightGray
                    )
                )
            }
        }
    }
}

@Composable
fun MealTimesSection(navController: NavController) {
    val mealTimes = listOf(
        "Morning" to "🌅",
        "Noon" to "☀️",
        "Evening" to "🌆",
        "Night" to "🌙"
    )

    Column {
        Text("Meal Times", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(mealTimes) { (name, emoji) ->
                Column(
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.Cravings.createRoute(name)) }
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color(0xFFF5F5F5), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(emoji, fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}


@Composable
fun CravingsSection(navController: NavController) {
    Column {
        Text("Cravings 🍜", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        
        // Craving name to drawable resource name mapping
        val cravings = listOf(
            Triple("Rainy Day", Color(0xFF64B5F6), "rainy_day"),
            Triple("Date Night", Color(0xFFE57373), "data_night"),
            Triple("Quick Meals", Color(0xFF81C784), "quick_meals"),
            Triple("Comfort Food", Color(0xFFFFB74D), "comfort_food"),
            Triple("Late Night", Color(0xFF9575CD), "late_night")
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cravings) { (name, fallbackColor, drawableName) ->
                Box(
                    modifier = Modifier
                        .size(width = 160.dp, height = 200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(fallbackColor)
                        .clickable { navController.navigate(Screen.Cravings.createRoute(name)) }
                ) {
                    // Background image
                    coil.compose.AsyncImage(
                        model = "android.resource://com.combocart/drawable/$drawableName",
                        contentDescription = name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                    
                    // Gradient overlay for text readability
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    ),
                                    startY = 100f
                                )
                            )
                    )
                    
                    // Text overlay
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(name, color = White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("See all >", color = White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun AchievementsSection() {
    Column {
        Text("Recent Achievements", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        
        // Horizontal badges
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(listOf("Italian Expert", "Spice King", "Early Bird")) { badge ->
                 Column(horizontalAlignment = Alignment.CenterHorizontally) {
                     Box(
                         modifier = Modifier
                             .size(60.dp)
                             .background(Color(0xFFFFF3E0), CircleShape), // Light orange bg
                         contentAlignment = Alignment.Center
                     ) {
                         Icon(Icons.Default.Star, contentDescription = null, tint = PrimaryOrange)
                     }
                     Spacer(modifier = Modifier.height(4.dp))
                     Text(badge, fontSize = 12.sp, color = DarkGray)
                 }
            }
        }
    }
}

@Composable
fun DealOfTheDaySection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryOrange) // Should be gradient
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("02:46:32", color = White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("FLAT 40% | Deal of the Day", color = White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Limited time offer!", color = White.copy(alpha = 0.8f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = {}, 
                colors = ButtonDefaults.textButtonColors(containerColor = White, contentColor = PrimaryOrange),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Grab Deal")
            }
        }
    }
}

@Composable
fun RetailBanner(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.RetailHome.route) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)), // Green as in image
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Store, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(28.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "Own a Retail Store?", 
                color = White, 
                fontWeight = FontWeight.Bold, 
                fontSize = 20.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "Join ComboCart and reach thousands\nof customers instantly!", 
                color = White.copy(alpha = 0.9f), 
                fontSize = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun TrendingSection(
    navController: NavController,
    state: HomeState
) {
    Column {
        Text("🔥 Trending Now", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        
        if (state.isLoading) {
             Text("Loading delicious recipes...", color = MediumGray)
        } else if (state.error != null) {
             Text("Error: ${state.error}", color = Color.Red)
        } else if (state.recipes.isEmpty()) {
             Text("No recipes found yet. Check back soon!", color = MediumGray)
        } else {
             Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                state.recipes.forEach { recipe ->
                        com.combocart.ui.components.RecipeItem(
                            name = recipe.title,
                            rating = String.format("%.1f", recipe.rating), // Format rating
                            price = "₹${recipe.price}",
                            isVeg = recipe.is_vegetarian,
                            time = "${recipe.cooking_time_minutes}m", // Mock or convert
                            imageUrl = recipe.image_url,
                            onClick = {
                                navController.navigate(Screen.RecipeDetail.createRoute(recipe.id))
                            }
                        )
                }
             }
        }
    }
}

