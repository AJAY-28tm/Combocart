package com.combocart.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.combocart.ui.theme.*

@Composable
fun RecipeItem(
    name: String, 
    rating: String, 
    price: String, 
    isVeg: Boolean, 
    time: String, 
    reviewCount: String = "1243",
    imageUrl: String? = null,
    onClick: () -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = White),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                if (!imageUrl.isNullOrEmpty()) {
                    coil.compose.AsyncImage(
                        model = imageUrl,
                        contentDescription = name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    val emoji = when {
                        name.contains("Butter Chicken", true) -> "🍗"
                        name.contains("Paneer", true) -> "🥘"
                        name.contains("Pasta", true) -> "🍝"
                        name.contains("Biryani", true) -> "🍲"
                        name.contains("Pizza", true) -> "🍕"
                        name.contains("Thai", true) -> "🥡"
                        else -> "🍱"
                    }
                    Text(emoji, fontSize = 32.sp)
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name, 
                    fontWeight = FontWeight.Bold, 
                    fontSize = 15.sp, 
                    color = Black
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star, 
                            contentDescription = null, 
                            tint = YellowRating, 
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = " ($reviewCount)", 
                        fontSize = 11.sp, 
                        color = MediumGray
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule, 
                        contentDescription = null, 
                        tint = MediumGray, 
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = " $time", 
                        fontSize = 12.sp, 
                        color = MediumGray
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = price, 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 14.sp, 
                        color = Black
                    )
                }
            }
        }
    }
}
