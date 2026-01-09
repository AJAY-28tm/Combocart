package com.combocart.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import com.combocart.ui.theme.PrimaryOrange
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.combocart.ui.components.ComboCartBottomBar
import com.combocart.ui.theme.White

import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import com.combocart.ui.components.RecipeItem
import com.combocart.ui.navigation.Screen
import com.combocart.ui.theme.Black
import com.combocart.ui.theme.DarkGray
import com.combocart.ui.theme.MediumGray


@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    // Mock data from Image 1
    val mockRecipes = listOf(
        MockRecipe("Butter Chicken Combo", "35 min", "₹299", "1243", "android.resource://com.combocart/drawable/butter_chicken_combo_img2"),
        MockRecipe("Paneer Tikka Masala", "30 min", "₹249", "892", "android.resource://com.combocart/drawable/paneer_tikka_masala_img3"),
        MockRecipe("Pasta Carbonara Kit", "25 min", "₹349", "2158", "android.resource://com.combocart/drawable/pasta_carbonara_kit_img4"),
        MockRecipe("Biryani Combo", "45 min", "₹399", "3421", "android.resource://com.combocart/drawable/biriyani_combo_img5"),
        MockRecipe("Pad Thai Kit", "20 min", "₹279", "756", "android.resource://com.combocart/drawable/pad_thai_kit_img6"),
        MockRecipe("Margherita Pizza Kit", "30 min", "₹229", "1089", "android.resource://com.combocart/drawable/margherita_pizza_kit_img7")
    )

    Scaffold(
        bottomBar = { ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(White)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Black)
                }
                Text(
                    "Search",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Search Row
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = state.query,
                            onValueChange = { viewModel.onQueryChange(it) },
                            modifier = Modifier.weight(1f).height(56.dp),
                            placeholder = { Text("Search for dishes, cuisines...", color = MediumGray, fontSize = 14.sp) },
                            shape = RoundedCornerShape(12.dp),
                            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                focusedBorderColor = PrimaryOrange,
                                focusedContainerColor = White,
                                unfocusedContainerColor = White
                            )
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                                .clickable { /* Voice Search */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Mic",
                                tint = DarkGray
                            )
                        }
                    }
                }

                // Recent Searches
                if (state.query.isEmpty()) {
                    item {
                        Column {
                            Text(
                                "Recent Searches", 
                                fontWeight = FontWeight.Bold, 
                                fontSize = 16.sp,
                                color = Black
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            androidx.compose.foundation.layout.FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                val recent = listOf("Biryani", "Pasta", "Paneer Tikka", "Butter Chicken")
                                recent.forEach { query ->
                                    RecentSearchChip(
                                        text = query,
                                        onClick = { viewModel.onQueryChange(query) }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    // Display mock recipes by default as in Image 1
                    items(mockRecipes) { recipe ->
                        RecipeItem(
                            name = recipe.name,
                            rating = "5.0",
                            price = recipe.price,
                            isVeg = true,
                            time = recipe.time,
                            reviewCount = recipe.reviewCount,
                            imageUrl = recipe.imageUrl,
                            onClick = {
                                // For mock, just go to first recipe
                                navController.navigate(Screen.RecipeDetail.createRoute("1"))
                            }
                        )
                    }
                } else if (state.isLoading) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                            androidx.compose.material3.CircularProgressIndicator(color = PrimaryOrange)
                        }
                    }
                } else if (state.searchResults.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                            Text("No results found for \"${state.query}\"", color = MediumGray)
                        }
                    }
                } else {
                    // Search Results
                    items(state.searchResults) { recipe ->
                        RecipeItem(
                            name = recipe.title,
                            rating = String.format("%.1f", recipe.rating),
                            price = "₹${recipe.price}",
                            isVeg = recipe.is_vegetarian,
                            time = "${recipe.cooking_time_minutes} min",
                            imageUrl = recipe.image_url,
                            onClick = {
                                navController.navigate(Screen.RecipeDetail.createRoute(recipe.id))
                            }
                        )
                    }
                }
                
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

data class MockRecipe(val name: String, val time: String, val price: String, val reviewCount: String, val imageUrl: String)

@Composable
fun RecentSearchChip(text: String, onClick: () -> Unit) {
    androidx.compose.material3.Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier.height(40.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp), contentAlignment = Alignment.Center) {
            Text(text, fontSize = 14.sp, color = DarkGray)
        }
    }
}
