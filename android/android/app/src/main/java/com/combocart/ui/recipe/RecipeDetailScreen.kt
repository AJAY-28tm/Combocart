package com.combocart.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.combocart.ui.recipe.RecipeDetailViewModel
import com.combocart.ui.recipe.RecipeDetailState
import com.combocart.ui.cart.CartViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.combocart.ui.theme.DarkGray
import com.combocart.ui.theme.LightGray
import com.combocart.ui.theme.MediumGray
import com.combocart.ui.theme.PrimaryOrange
import com.combocart.ui.theme.White

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: String?,
    viewModel: RecipeDetailViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    cartViewModel: CartViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val recipe = state.recipe
    val context = LocalContext.current

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PrimaryOrange)
        }
    } else if (state.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
             Text("Error: ${state.error}", color = Color.Red)
             Button(onClick = { navController.popBackStack() }) { Text("Go Back") }
        }
    } else if (recipe != null) {
        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                CenterAlignedTopAppBar(
                    title = { Text("Italian Expert", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Favorite */ }) {
                            Icon(Icons.Default.Favorite, contentDescription = "Favorite", tint = Color(0xFF2D3436))
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = White)
                )
            },
            bottomBar = { com.combocart.ui.components.ComboCartBottomBar(navController) }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(White)
            ) {
                // Main Image with Rating Badge
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp)
                            .height(240.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5)), contentAlignment = Alignment.Center) {
                                if (!recipe.image_url.isNullOrEmpty()) {
                                    coil.compose.AsyncImage(
                                        model = recipe.image_url,
                                        contentDescription = recipe.title,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                    )
                                } else {
                                    Text("🍝", fontSize = 80.sp)
                                }
                            }
                        }
                        
                        // Rating Badge (Image pattern match)
                        Surface(
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.TopEnd),
                            shape = RoundedCornerShape(12.dp),
                            color = White,
                            shadowElevation = 4.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(recipe.rating.toString(), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(2.dp))
                                Icon(Icons.Default.Star, null, tint = PrimaryOrange, modifier = Modifier.size(12.dp))
                            }
                        }
                    }
                }

                // Title & Description
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = recipe.title, 
                            fontSize = 24.sp, 
                            fontWeight = FontWeight.ExtraBold, 
                            color = Color(0xFF1B242D)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = recipe.description ?: "Classic Roman pasta with guanciale, eggs, and pecorino cheese", 
                            color = Color(0xFF7F8C8D), 
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                // Info Cards with background colors
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ColoredInfoCard(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Schedule,
                            label = "Prep Time",
                            value = "25 mins",
                            bgColor = Color(0xFFFFF8F1),
                            iconColor = Color(0xFFE67E22)
                        )
                        ColoredInfoCard(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Group,
                            label = "Serves",
                            value = "4 people",
                            bgColor = Color(0xFFF1FFF4),
                            iconColor = Color(0xFF27AE60),
                            onClick = { navController.navigate(Screen.Serves.createRoute(recipe.id)) }
                        )
                        ColoredInfoCard(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.LocalFireDepartment,
                            label = "Calories",
                            value = "580 kcal",
                            bgColor = Color(0xFFF1F6FF),
                            iconColor = Color(0xFF2980B9),
                            onClick = { navController.navigate(Screen.Nutrition.createRoute(recipe.id)) }
                        )
                    }
                }

                // Preparation Video Section (moved here as per image)
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Videocam, null, tint = PrimaryOrange, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Preparation Video", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Black)
                        ) {
                            Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .align(Alignment.Center)
                                    .background(White.copy(alpha=0.9f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PlayArrow, null, tint = PrimaryOrange, modifier = Modifier.size(32.dp).padding(start=4.dp))
                            }
                            Text(
                                "8:42", 
                                color = White, 
                                fontSize = 11.sp,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(12.dp)
                                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                // Ingredients Section (Pattern from Image)
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("📋", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Ingredients (Customizable)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        RecipeIngredientRow("Spaghetti - 400g", true)
                        RecipeIngredientRow("Guanciale - 150g", true)
                        RecipeIngredientRow("Eggs - 4 large", true)
                        RecipeIngredientRow("Pecorino Romano - 100g", true)
                        RecipeIngredientRow("Black Pepper - 2 tsp", true)
                        RecipeIngredientRow("Salt - to taste", false)
                    }
                }

                // Action Buttons: Serves & Portions
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { navController.navigate(Screen.Serves.createRoute(recipe.id)) },
                            modifier = Modifier.weight(1f).height(54.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                        ) {
                            Text("🍴 Serves", fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(
                            onClick = { navController.navigate(Screen.Customize.createRoute(recipe.id)) },
                            modifier = Modifier.weight(1f).height(54.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryOrange),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryOrange)
                        ) {
                            Text("🌐 Portions", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Nutrition Facts
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nutrition Facts", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            NutritionItem(label = "Calories", value = "520", modifier = Modifier.weight(1f), color = Color(0xFF00A355))
                            NutritionItem(label = "Protein", value = "28g", modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            NutritionItem(label = "Carbs", value = "45g", modifier = Modifier.weight(1f))
                            NutritionItem(label = "Fat", value = "22g", modifier = Modifier.weight(1f))
                        }
                    }
                }

                // Add to Cart (Green Button)
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Button(
                            onClick = { 
                                cartViewModel.addToCart(recipe.id!!, recipe)
                                Toast.makeText(context, "Added to Cart!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A355))
                        ) {
                            Text("Add to Cart", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = White)
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
             Text("Recipe not found", color = MediumGray)
        }
    }
}


@Composable
fun RecipeIngredientRow(name: String, isChecked: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(48.dp)
            .background(Color(0xFFF9FAFB), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkedState = remember { androidx.compose.runtime.mutableStateOf(isChecked) }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF3498DB))
        )
        Text(name, color = Color(0xFF2D3436), modifier = Modifier.weight(1f), fontSize = 14.sp)
        Text(
            "Edit", 
            color = PrimaryOrange, 
            fontSize = 12.sp, 
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { /* Edit Ingredient */ }
        )
    }
}

@Composable
fun ColoredInfoCard(modifier: Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String, bgColor: Color, iconColor: Color, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, null, tint = iconColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, fontSize = 11.sp, color = Color(0xFF7F8C8D))
            Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF2D3436))
        }
    }
}

@Composable
fun NutritionItem(label: String, value: String, modifier: Modifier, color: Color = Color(0xFF2D3436)) {
    Card(
        modifier = modifier.height(70.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = color)
            Text(label, fontSize = 11.sp, color = Color(0xFF7F8C8D))
        }
    }
}
