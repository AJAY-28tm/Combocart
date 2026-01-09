package com.combocart.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import com.combocart.ui.navigation.Screen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.combocart.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetailStoreDetailScreen(navController: NavController, storeId: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Store Details", color = White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryOrange)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF9F9F9)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Fresh Mart Grocery", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text("Koramangala 4th Block, Bangalore", color = MediumGray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, null, tint = YellowRating)
                            Text(" 4.8 (250+ reviews)", fontWeight = FontWeight.Bold)
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = Color(0xFFEEEEEE))
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // New Info Section
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                             StoreStatItem("Items", "1,200+")
                             StoreStatItem("In Stock", "98%")
                             StoreStatItem("Delivery", "15m")
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text("Famous For:", fontSize = 12.sp, color = MediumGray, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                            listOf("Organic", "Fresh", "Vegetables", "Discount").forEach { tag ->
                                SuggestionChip(
                                    onClick = {}, 
                                    label = { Text(tag) }, 
                                    colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFFFFF3E0)),
                                    border = null,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text("Popular Categories", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            items(listOf(
                 "Fruits & Vegetables" to "120+ items", 
                 "Dairy & Eggs" to "50+ items",
                 "Beverages" to "80+ items", 
                 "Snacks" to "200+ items"
            )) { (category, count) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(category, fontWeight = FontWeight.Medium)
                            Text(count, fontSize = 12.sp, color = MediumGray)
                        }
                        // Removed ChevronRight icon as requested
                    }
                }
            }
        }
    }
}

@Composable
fun StoreStatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryOrange)
        Text(label, fontSize = 12.sp, color = MediumGray)
    }
}
