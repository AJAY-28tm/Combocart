package com.combocart.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun ReviewScreen(
    navController: NavController, 
    recipeId: String? = null
) {
    Scaffold(
        bottomBar = { com.combocart.ui.components.ComboCartBottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFBFBFB))
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF1A1A1A))
                }
                Text(
                    "Reviews", 
                    fontSize = 18.sp, 
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Summary Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "4.8", 
                                fontSize = 56.sp, 
                                fontWeight = FontWeight.ExtraBold, 
                                color = Color(0xFF1A1A1A)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                repeat(5) {
                                    Icon(
                                        imageVector = Icons.Default.Star, 
                                        contentDescription = null, 
                                        tint = Color(0xFFFFD700),
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Based on 1243 reviews", 
                                fontSize = 13.sp, 
                                color = Color(0xFF7F8C8D)
                            )
                        }
                    }
                }

                val mockReviews = listOf(
                    ReviewData(
                        "Priya Sharma", 
                        "1/15/2024", 
                        5, 
                        "Absolutely delicious! The ingredients were so fresh and the recipe was easy to follow.",
                        listOf("🍗"),
                        "https://randomuser.me/api/portraits/women/1.jpg"
                    ),
                    ReviewData(
                        "Rahul Verma", 
                        "1/14/2024", 
                        4, 
                        "Great combo! Delivery was super fast. Only wish there was more sauce.",
                        emptyList(),
                        "https://randomuser.me/api/portraits/men/1.jpg"
                    ),
                    ReviewData(
                        "Ananya Patel", 
                        "1/13/2024", 
                        5, 
                        "Best butter chicken I've made at home! Restaurant quality.",
                        listOf("🥘", "🥘"),
                        "https://randomuser.me/api/portraits/women/2.jpg"
                    )
                )

                items(mockReviews) { review ->
                    ReviewCard(review)
                }
                
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun ReviewCard(data: ReviewData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(0xFFEEEEEE), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(data.name.first().toString(), fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        data.name, 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 15.sp,
                        color = Color(0xFF1A1A1A)
                    )
                    Text(
                        data.date, 
                        fontSize = 12.sp, 
                        color = Color(0xFF7F8C8D)
                    )
                }

                Row {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star, 
                            contentDescription = null, 
                            tint = if (index < data.rating) Color(0xFFFFD700) else Color(0xFFEEEEEE),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = data.comment, 
                fontSize = 14.sp, 
                color = Color(0xFF444444),
                lineHeight = 20.sp
            )
            
            if (data.images.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    data.images.forEach { emoji ->
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(emoji, fontSize = 32.sp)
                        }
                    }
                }
            }
        }
    }
}

data class ReviewData(
    val name: String,
    val date: String,
    val rating: Int,
    val comment: String,
    val images: List<String>,
    val avatarUrl: String
)
